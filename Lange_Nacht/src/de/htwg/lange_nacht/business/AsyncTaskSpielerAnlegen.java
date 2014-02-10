package de.htwg.lange_nacht.business;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.CoreConnectionPNames;
import org.apache.http.params.HttpParams;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Message;
import android.os.Messenger;
import android.util.Log;
import de.htwg.lange_nacht.data.Messages;

public class AsyncTaskSpielerAnlegen extends AsyncTask<Void, Void, Void> {

	private Messenger messenger;
	private String vorname;
	private String nachname;
	private HttpResponse response;

	public AsyncTaskSpielerAnlegen(Messenger messenger, String vorname,
			String nachname) {
		this.messenger = messenger;
		this.vorname = vorname;
		this.nachname = nachname;
	}

	@Override
	protected Void doInBackground(Void... params) {
		// URL zusammenbauen
		String url = Strafenverwaltung.SERVER_IP+"/langenacht/insertSpieler.php?";
		List<NameValuePair> parameter = new LinkedList<NameValuePair>();
		parameter.add(new BasicNameValuePair("vorname", vorname));
		parameter.add(new BasicNameValuePair("nachname", nachname));

		String paramString = URLEncodedUtils.format(parameter, "utf-8");
		url += paramString;

		HttpClient httpclient = new DefaultHttpClient();

		// Timeout setzen, falls Server nicht erreichbar ist
		int timeout = 5; // seconds
		HttpParams httpParams = httpclient.getParams();
		httpParams.setParameter(CoreConnectionPNames.CONNECTION_TIMEOUT,
				timeout * 1000);
		httpParams
				.setParameter(CoreConnectionPNames.SO_TIMEOUT, timeout * 1000);
		response = null;
		HttpGet httpget = new HttpGet(url);
		try {
			response = httpclient.execute(httpget);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	protected void onPostExecute(Void result) {
		super.onPostExecute(result);
		Message msg = Message.obtain();
		if (response != null) {
			msg.arg1 = Activity.RESULT_OK;
		} else {
			msg.arg1 = Activity.RESULT_CANCELED;
		}

		msg.arg2 = Messages.SET_SPIELER;

		try {
			messenger.send(msg);
		} catch (android.os.RemoteException e1) {
			Log.w(getClass().getName(), "Exception while sending message", e1);
		}
	}

}
