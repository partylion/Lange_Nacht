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
import de.htwg.lange_nacht.data.Strafe;

public class AsyncTaskOffeneStrafenFor extends AsyncTask<Void, Void, Void> {

	private Context context;
	private Messenger messenger;
	private String vorname;
	private String nachname;

	private ProgressDialog progressDialog;
	private ArrayList<Strafe> strafen;

	private String TAG = this.getClass().getSimpleName();

	public AsyncTaskOffeneStrafenFor(Context context, Messenger messenger, String vorname, String nachname) {
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
		msg.arg2 = Messages.GET_OFFENE_STRAFEN_FOR;
		msg.obj = strafen;
		try {
			messenger.send(msg);
		} catch (android.os.RemoteException e1) {
			Log.w(getClass().getName(), "Exception while sending message", e1);
		}
	}

	private void getAllStrafen() {
		strafen = new ArrayList<Strafe>();
		// Adresse zu PHP-Datei
		// Zuhause
		String url = "http://37.49.36.97/langenacht/getOffeneStrafenFor.php?";
		//Konstanz
//		String url = "http://95.208.211.117/langenacht/getAllStrafenFor.php?";
		
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
		
		System.out.println(url);

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
			// Aus Rückgabewert der PHP-Datei einen JSONArray bauen
			JSONArray jsonArray = null;
			String data = "";
			StatusLine statusLine = response.getStatusLine();
			int statusCode = statusLine.getStatusCode();
			System.out.println(statusCode);
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
						strafen.add(new Strafe(row.getString("strafenID"), row
								.getString("beschreibung"), Integer
								.parseInt(row.getString("preis"))));
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
			strafen = null;
			Log.d(TAG, "Daten konnten nicht geladen werden");
		}
	}
}