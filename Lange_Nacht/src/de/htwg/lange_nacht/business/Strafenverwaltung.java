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
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.os.Messenger;
import android.util.Log;
import de.htwg.lange_nacht.data.Spieler;
import de.htwg.lange_nacht.data.Strafe;

public class Strafenverwaltung implements IStrafenverwaltung {

	private String TAG = this.getClass().getSimpleName();

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
	 * Gibt das einzige Objekt der Klasse Strafenverwaltung zurück, das erzeugt
	 * werden darf
	 * 
	 * @return das einzige instanzierte Objekt der Klasse
	 */
	public static Strafenverwaltung getInstance() {
		return strafenverwaltungsinstanz;
	}

	@Override
	public void getAllSpielerAndStrafen(Context context, Messenger messenger) {
		// Call AsyncTask
		new AsyncTaskAlleSpielerUndStrafen(context, messenger).execute();
	}

	@Override
	public Strafe[] getStrafenFor(String vorname, String nachname) {
		// TODO Strafen für Spieler aus Datenhaltung holen
		return dummyStrafenerzeugen();
	}

	@Override
	public void spielerAnlegen(Messenger messenger, String vorname,
			String nachname) {

		new AsyncTaskSpielerAnlegen(messenger, vorname, nachname).execute();

	}

	@Override
	public void strafeAnlegen(Messenger messenger, String beschreibung,
			String preis) {

		new AsyncTaskStrafeAnlegen(messenger, beschreibung, preis).execute();
	}

	@Override
	public ArrayList<Strafe> getAllStrafen() {

		ArrayList<Strafe> alleStrafen = new ArrayList<Strafe>();
		// PHP-Datei aufrufen
		String url = "http://37.49.36.97/langenacht/getAllStrafen.php";
		HttpClient httpclient = new DefaultHttpClient();
		HttpGet httpget = new HttpGet(url);
		HttpResponse response = null;
		try {
			response = httpclient.execute(httpget);
		} catch (IOException e) {
			e.printStackTrace();
		}

		// Aus Rückgabewert der PHP-Datei einen JSONArray bauen
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
					JSONObject row = jsonArray.getJSONObject(i);
					alleStrafen.add(new Strafe(row.getString("strafenID"), row
							.getString("beschreibung"), Integer.parseInt(row
							.getString("preis"))));
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

		return alleStrafen;
	}

	@Override
	public void vergehenAnlegen(Messenger messenger, Spieler spieler,
			Strafe strafe, String datum) {
		new AsyncTaskVergehenAnlegen(messenger, spieler, strafe, datum)
				.execute();
	}

	@Override
	public void getAllSpieler(Context context, Messenger messenger) {
		new AsyncTaskAlleSpieler(context, messenger).execute();
	}

}
