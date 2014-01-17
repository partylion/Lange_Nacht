package de.htwg.lange_nacht.business;

import java.util.ArrayList;

import android.content.Context;
import android.os.Messenger;
import de.htwg.lange_nacht.data.Spieler;
import de.htwg.lange_nacht.data.Strafe;

public interface IStrafenverwaltung {

	/**
	 * Holt sich alle vorhandenen Spieler von der Datenhaltung und gibt diese
	 * als Arraylist zur�ck
	 * 
	 * @return ein String Array mit allen Spielern
	 */
	public void getAllSpielerAndStrafen(Context context, Messenger messenger);

	/**
	 * Gibt alle Strafen f�r den �bergebenen Spieler zur�ck
	 * 
	 * @param spieler
	 * @return Alle Strafen des Spielers als Array
	 */
	public Strafe[] getStrafenFor(String vorname, String nachname);

	/**
	 * Ruft eine PHP-Datei auf um einen neuen Spieler mit den �bergebenen
	 * Parametern anzulegen
	 * 
	 * @param vorname
	 * @param nachname
	 */
	public void spielerAnlegen(String vorname, String nachname);

	/**
	 * Ruft eine PHP-Datei auf um eine neue Strafe mit den �bergebenen
	 * Parametern anzulegen
	 * 
	 * @param beschreibung
	 * @param preis
	 */
	public void strafeAnlegen(String beschreibung, String preis);

	/**
	 * Holt sich alle vorhandenen Strafen von der Datenhaltung und gibt sie als
	 * Araylist zur�ck
	 * 
	 * @return eine Arraylist mit allen Strafen
	 */
	public ArrayList<Strafe> getAllStrafen();
	
	/**
	 * Ruft eine PHP-Datei auf um ein neues Vergehen mit den �bergebenen
	 * Parametern anzulegen
	 * 
	 * @param spieler
	 * @param strafe
	 */
	public void vergehenAnlegen(Messenger messenger, Spieler spieler, Strafe strafe, String datum);
}
