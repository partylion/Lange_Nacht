package de.htwg.lange_nacht.business;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Message;
import android.os.Messenger;
import android.util.Log;
import de.htwg.lange_nacht.data.Messages;
import de.htwg.lange_nacht.data.Spieler;
import de.htwg.lange_nacht.data.Strafe;

public class AsyncTaskVergehenAnlegen extends AsyncTask<Void, Void, Void>{
	
	private Messenger messenger;
	private Spieler spieler;
	private Strafe strafe;
	private String datum;

	public AsyncTaskVergehenAnlegen(Messenger messenger, Spieler spieler, Strafe strafe, String datum) {
		this.messenger = messenger;
		this.spieler=spieler;
		this.strafe=strafe;
		this.datum=datum;
	}

	
	@Override
	protected Void doInBackground(Void... params) {
		//Zuhause
		String url = Strafenverwaltung.SERVER_IP+"/langenacht/insertVergehen.php?";

		List<NameValuePair> parameter = new LinkedList<NameValuePair>();
		parameter.add(new BasicNameValuePair("spielerID", spieler.getID()));
		parameter.add(new BasicNameValuePair("strafenID", strafe.getID()));
		parameter.add(new BasicNameValuePair("datum", datum));

		String paramString = URLEncodedUtils.format(parameter, "utf-8");
		url += paramString;

		HttpClient httpclient = new DefaultHttpClient();
		HttpGet httpget = new HttpGet(url);
		try {
			httpclient.execute(httpget);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	protected void onPostExecute(Void result) {
		super.onPostExecute(result);
		Message msg = Message.obtain();
		msg.arg1 = Activity.RESULT_OK;
		msg.arg2 = Messages.SET_VERGEHEN;
		try {
			messenger.send(msg);
		} catch (android.os.RemoteException e1) {
			Log.w(getClass().getName(), "Exception while sending message", e1);
		}
	}

}
