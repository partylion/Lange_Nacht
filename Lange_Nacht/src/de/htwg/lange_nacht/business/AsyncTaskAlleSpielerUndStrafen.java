package de.htwg.lange_nacht.business;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
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
import de.htwg.lange_nacht.data.Spieler;
import de.htwg.lange_nacht.data.Strafe;

public class AsyncTaskAlleSpielerUndStrafen extends AsyncTask<Void, Void, Void> {

	private Context context;
	private Messenger messenger;

	private ProgressDialog progressDialog;
	private ArrayList<Spieler> spieler;
	private ArrayList<Strafe> strafen;

	private String TAG = this.getClass().getSimpleName();

	public AsyncTaskAlleSpielerUndStrafen(Context context, Messenger messenger) {
		this.context = context;
		this.messenger = messenger;
	}

	protected void onPreExecute() {
		super.onPreExecute();
		progressDialog = ProgressDialog.show(context, "Fetching",
				"Please wait...");
	}

	@Override
	protected Void doInBackground(Void... arg0) {
		getAllSpieler();
		getAllStrafen();
		return null;
	}

	protected void onPostExecute(Void result) {
		super.onPostExecute(result);
		progressDialog.dismiss();
		Message msg = Message.obtain();
		msg.arg1 = Activity.RESULT_OK;
		msg.arg2 = Messages.GET_SPIELER;
		msg.obj = spieler;
		Message msg2 = Message.obtain();
		msg2.arg1 = Activity.RESULT_OK;
		msg2.obj = strafen;
		msg2.arg2 = Messages.GET_STRAFEN;
		try {
			messenger.send(msg);
			messenger.send(msg2);
		} catch (android.os.RemoteException e1) {
			Log.w(getClass().getName(), "Exception while sending message", e1);
		}
	}

	private void getAllSpieler() {
		spieler = new ArrayList<Spieler>();
		// Adresse zu PHP-Datei
		String url = Strafenverwaltung.SERVER_IP+"/langenacht/getAllSpieler.php";
		
		HttpClient httpclient = new DefaultHttpClient();
		// Timeout setzen, falls Server nicht erreichbar ist
		int timeout = 5; // seconds
		HttpParams httpParams = httpclient.getParams();
		httpParams.setParameter(CoreConnectionPNames.CONNECTION_TIMEOUT,
				timeout * 1000);
		httpParams
				.setParameter(CoreConnectionPNames.SO_TIMEOUT, timeout * 1000);

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

					// Aus dem JSONArray die Spieler auslesen und als Arraylist
					// von Spieler-Objekten speichern
					for (int i = 0; i < jsonArray.length(); i++) {
						JSONObject row = jsonArray.getJSONObject(i);
						spieler.add(new Spieler(row.getString("idSpieler"), row
								.getString("vorname"), row
								.getString("nachname")));
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
			spieler = null;
			Log.d(TAG, "Daten konnten nicht geladen werden");
		}
	}

	public void getAllStrafen() {
		strafen = new ArrayList<Strafe>();
		// Adresse zu PHP-Datei
		String url = Strafenverwaltung.SERVER_IP+"/langenacht/getAllStrafen.php";
		HttpClient httpclient = new DefaultHttpClient();

		// Timeout setzen, falls Server nicht erreichbar ist
		int timeout = 5; // seconds
		HttpParams httpParams = httpclient.getParams();
		httpParams.setParameter(CoreConnectionPNames.CONNECTION_TIMEOUT,
				timeout * 1000);
		httpParams
				.setParameter(CoreConnectionPNames.SO_TIMEOUT, timeout * 1000);

		// PHP-Datei aufrufen
		HttpGet httpget = new HttpGet(url);
		HttpResponse response = null;
		try {
			response = httpclient.execute(httpget);
		} catch (IOException e) {
			e.printStackTrace();
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
			Log.d(TAG, "Daten konnten nicht geladen werden");
			strafen = null;
		}
	}

}