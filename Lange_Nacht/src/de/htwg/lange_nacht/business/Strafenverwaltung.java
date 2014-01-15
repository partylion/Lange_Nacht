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
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.util.Log;
import de.htwg.lange_nacht.data.Spieler;
import de.htwg.lange_nacht.data.Strafe;

public class Strafenverwaltung implements IStrafenverwaltung {

	private String TAG = this.getClass().getSimpleName();
	private String[] dummyBeschreibungen = { "Strafe ausw�hlen", "Strafe1",
			"Strafe2", "Strafe3", "Strafe4", "Strafe5" };

	// private String[] dummySpieler = { "Spieler ausw�hlen", "Spieler 1",
	// "Spieler 2", "Spieler 3", "Spieler 4", "Spieler 5" };

	private Strafe[] dummyStrafenerzeugen() {

		Strafe strafe1 = new Strafe("Strafe1", 20);
		Strafe strafe2 = new Strafe("Strafe2", 50);
		Strafe strafe3 = new Strafe("Strafe1", 20);

		Strafe[] dummyStrafen = { strafe1, strafe2, strafe3 };

		return dummyStrafen;
	}

	protected Strafenverwaltung() {

	}

	private static Strafenverwaltung strafenverwaltungsinstanz = new Strafenverwaltung();

	/**
	 * Gibt das einzige Objekt der Klasse Strafenverwaltung zur�ck, das erzeugt
	 * werden darf
	 * 
	 * @return das einzige instanzierte Objekt der Klasse
	 */
	public static Strafenverwaltung getInstance() {
		return strafenverwaltungsinstanz;
	}

	@Override
	public ArrayList<Spieler> getAllSpieler() {
		ArrayList<Spieler> alleSpieler = new ArrayList<Spieler>();
		// PHP-Datei aufrufen
		String url = "http://10.0.2.2/langenacht/getAllSpieler.php";
		HttpClient httpclient = new DefaultHttpClient();
		HttpGet httpget = new HttpGet(url);
		HttpResponse response = null;
		try {
			response = httpclient.execute(httpget);
		} catch (IOException e) {
			e.printStackTrace();
		}

		// Aus R�ckgabewert der PHP-Datei einen JSONArray bauen
		JSONArray jsonArray = null;
		String data = "";
		StatusLine statusLine = response.getStatusLine();
		int statusCode = statusLine.getStatusCode();
		if (statusCode == 200) {
			try {
				BufferedReader br = new BufferedReader(new InputStreamReader(
						response.getEntity().getContent()));
				String line;
				while ((line = br.readLine()) != null) {
					data += line;
				}

				jsonArray = new JSONArray(data);

				// Aus dem JSONArray die Spieler auslesen und als Arraylist von
				// Spieler-Objekten speichern

				for (int i = 0; i < jsonArray.length(); i++) {
					System.out.println("test");
					JSONObject row = jsonArray.getJSONObject(i);
					System.out.println("test2");
					alleSpieler.add(new Spieler(row.getString("vorname"), row
							.getString("nachname")));
				}

			} catch (IllegalStateException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} else {
			Log.d(TAG, "Daten konnten nicht geladen werden");
		}

		return alleSpieler;

	}

	@Override
	public String[] getAllBeschreibungen() {
		// TODO Logik implementieren
		return dummyBeschreibungen;
	}

	@Override
	public int getPreisFor(String beschreibung) {

		int preis = 0;

		// TODO Logik einbauen

		return preis;
	}

	@Override
	public Strafe[] getStrafenFor(String vorname, String nachname) {
		// TODO Strafen f�r Spieler aus Datenhaltung holen
		return dummyStrafenerzeugen();
	}

	@Override
	public void spielerAnlegen(String vorname, String nachname) {
		String url = "http://10.0.2.2/langenacht/insertSpieler.php?";

		List<NameValuePair> params = new LinkedList<NameValuePair>();
		params.add(new BasicNameValuePair("vorname", vorname));
		params.add(new BasicNameValuePair("nachname", nachname));

		String paramString = URLEncodedUtils.format(params, "utf-8");
		url += paramString;

		HttpClient httpclient = new DefaultHttpClient();
		HttpGet httpget = new HttpGet(url);
		try {
			httpclient.execute(httpget);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void strafeAnlegen(String beschreibung, String preis) {
		String url = "http://10.0.2.2/langenacht/insertStrafe.php?";

		List<NameValuePair> params = new LinkedList<NameValuePair>();
		params.add(new BasicNameValuePair("beschreibung", beschreibung));
		params.add(new BasicNameValuePair("preis", preis));

		String paramString = URLEncodedUtils.format(params, "utf-8");
		url += paramString;

		HttpClient httpclient = new DefaultHttpClient();
		HttpGet httpget = new HttpGet(url);
		try {
			httpclient.execute(httpget);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
