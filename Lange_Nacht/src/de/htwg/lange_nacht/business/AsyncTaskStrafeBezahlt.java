package de.htwg.lange_nacht.business;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.CoreConnectionPNames;
import org.apache.http.params.HttpParams;

import de.htwg.lange_nacht.data.Messages;
import android.app.Activity;
import android.os.AsyncTask;
import android.os.Message;
import android.os.Messenger;
import android.util.Log;

public class AsyncTaskStrafeBezahlt extends AsyncTask<Void, Void, Void> {

	private Messenger messenger;
	private Date datum;
	private String vorname, nachname;
	private String strafenID;
	private HttpResponse response;

	public AsyncTaskStrafeBezahlt(Messenger messenger, String vorname,
			String nachname, String strafenID) {
		this.messenger = messenger;
		this.vorname = vorname;
		this.nachname = nachname;
		this.strafenID = strafenID;
	}

	@Override
	protected Void doInBackground(Void... params) {
		// URL zusammenbauen
		String url = "http://37.49.36.97/langenacht/updateVergehen.php?";
		List<NameValuePair> parameter = new LinkedList<NameValuePair>();
		parameter.add(new BasicNameValuePair("strafenID", strafenID));
		parameter.add(new BasicNameValuePair("vorname", vorname));
		parameter.add(new BasicNameValuePair("nachname", nachname));

		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.US);
		datum = new Date();

		parameter.add(new BasicNameValuePair("datum", sdf.format(datum)));

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
		msg.arg2 = Messages.UPDATE_STRAFE;
		try {
			messenger.send(msg);
		} catch (android.os.RemoteException e1) {
			Log.w(getClass().getName(), "Exception while sending message", e1);
		}
	}

}
