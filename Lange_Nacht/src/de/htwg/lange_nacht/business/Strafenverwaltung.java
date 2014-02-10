package de.htwg.lange_nacht.business;

import android.content.Context;
import android.os.Messenger;
import de.htwg.lange_nacht.data.Spieler;
import de.htwg.lange_nacht.data.Strafe;
import de.htwg.lange_nacht.data.Vergehen;

public class Strafenverwaltung implements IStrafenverwaltung {


	protected Strafenverwaltung() {

	}
	
	public static final String SERVER_IP = "http://78.43.50.150";

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
	public void getAllStrafenFor(Context context, Messenger messenger, String vorname, String nachname) {
		// TODO Strafen für Spieler aus Datenhaltung holen
		new AsyncTaskAlleStrafenFor(context, messenger, vorname, nachname).execute();
	}
	

	@Override
	public void getOffeneStrafenFor(Context context, Messenger messenger, String vorname, String nachname) {
		new AsyncTaskOffeneStrafenFor(context, messenger, vorname, nachname).execute();
	}

	@Override
	public void getBezahlteStrafenFor(Context context, Messenger messenger, String vorname,
			String nachname) {
		new AsyncTaskBezahlteStrafenFor(context, messenger, vorname, nachname).execute();
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
	public void vergehenAnlegen(Messenger messenger, Spieler spieler,
			Strafe strafe, String datum) {
		new AsyncTaskVergehenAnlegen(messenger, spieler, strafe, datum)
				.execute();
	}

	@Override
	public void getAllSpieler(Context context, Messenger messenger) {
		new AsyncTaskAlleSpieler(context, messenger).execute();
	}

	@Override
	public void setBezahlt(Messenger messenger, Vergehen vergehen) {
		new AsyncTaskStrafeBezahlt(messenger, vergehen).execute();
	}

	@Override
	public void getAllOffeneStrafen(Context context, Messenger messenger) {
		new AsyncTaskAlleOffenenStrafen(context, messenger).execute();		
	}

}
