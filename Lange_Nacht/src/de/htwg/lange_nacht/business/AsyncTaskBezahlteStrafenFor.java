package de.htwg.lange_nacht.business;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.StatusLine;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.CoreConnectionPNames;
import org.apache.http.params.HttpParams;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Message;
import android.os.Messenger;
import android.util.Log;
import de.htwg.lange_nacht.data.Messages;
import de.htwg.lange_nacht.data.Vergehen;

public class AsyncTaskBezahlteStrafenFor extends AsyncTask<Void, Void, Void> {

	private Context context;
	private Messenger messenger;
	private String vorname;
	private String nachname;

	private ProgressDialog progressDialog;
	private ArrayList<Vergehen> vergehen;

	private String TAG = this.getClass().getSimpleName();

	public AsyncTaskBezahlteStrafenFor(Context context, Messenger messenger, String vorname, String nachname) {
		this.context = context;
		this.messenger = messenger;
		this.vorname = vorname;
		this.nachname = nachname;
	}

	protected void onPreExecute() {
		super.onPreExecute();
		progressDialog = ProgressDialog.show(context, "Fetching",
				"Please wait...");
	}

	@Override
	protected Void doInBackground(Void... arg0) {
		getAllStrafen();
		return null;
	}

	protected void onPostExecute(Void result) {
		super.onPostExecute(result);
		progressDialog.dismiss();
		Message msg = Message.obtain();
		msg.arg1 = Activity.RESULT_OK;
		msg.arg2 = Messages.GET_BEZAHLTE_STRAFEN_FOR;
		msg.obj = vergehen;
		try {
			messenger.send(msg);
		} catch (android.os.RemoteException e1) {
			Log.w(getClass().getName(), "Exception while sending message", e1);
		}
	}

	private void getAllStrafen() {
		vergehen = new ArrayList<Vergehen>();
		// Adresse zu PHP-Datei
		String url = Strafenverwaltung.SERVER_IP+"/langenacht/getBezahlteStrafenFor.php?";
		
		HttpClient httpclient = new DefaultHttpClient();
		// Timeout setzen, falls Server nicht erreichbar ist
		int timeout = 5; // seconds
		HttpParams httpParams = httpclient.getParams();
		httpParams.setParameter(CoreConnectionPNames.CONNECTION_TIMEOUT,
				timeout * 1000);
		httpParams
				.setParameter(CoreConnectionPNames.SO_TIMEOUT, timeout * 1000);
		
		List<NameValuePair> parameter = new LinkedList<NameValuePair>();
		parameter.add(new BasicNameValuePair("vorname", vorname));
		parameter.add(new BasicNameValuePair("nachname", nachname));

		String paramString = URLEncodedUtils.format(parameter, "utf-8");
		url += paramString;

		// PHP-Datei aufrufen
		HttpGet httpget = new HttpGet(url);
		HttpResponse response = null;
		try {
			response = httpclient.execute(httpget);
		} catch (IOException e) {
			System.out.println("Verbindung wurde abgebrochen");
		}
		// Wenn alles gut ging
		if (response != null) {
			// Aus R�ckgabewert der PHP-Datei einen JSONArray bauen
			JSONArray jsonArray = null;
			String data = "";
			StatusLine statusLine = response.getStatusLine();
			int statusCode = statusLine.getStatusCode();
			if (statusCode == 200) {
				try {
					BufferedReader br = new BufferedReader(
							new InputStreamReader(response.getEntity()
									.getContent()));
					String line;
					while ((line = br.readLine()) != null) {
						data += line;
					}
					jsonArray = new JSONArray(data);

					// Aus dem JSONArray die Strafen auslesen und als Arraylist
					// von Strafen-Objekten speichern
					for (int i = 0; i < jsonArray.length(); i++) {
						JSONObject row = jsonArray.getJSONObject(i);
						vergehen.add(new Vergehen(row.getString("beschreibung"),
								Integer.parseInt(row.getString("preis")),
								Integer.parseInt(row.getString("strafenID")),
								row.getString("vorname"), row
										.getString("nachname"), Integer
										.parseInt(row.getString("spielerID")),
								row.getString("datum")));
					}
				} catch (IllegalStateException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				} catch (JSONException e) {
					e.printStackTrace();
				}
			}
		}
		// Wenn es einen Timeout gab
		else {
			vergehen = null;
			Log.d(TAG, "Daten konnten nicht geladen werden");
		}
	}
}