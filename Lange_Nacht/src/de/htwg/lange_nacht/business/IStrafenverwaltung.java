package de.htwg.lange_nacht.business;

import java.util.ArrayList;

import android.content.Context;
import android.os.Messenger;
import de.htwg.lange_nacht.data.Spieler;
import de.htwg.lange_nacht.data.Strafe;

public interface IStrafenverwaltung {

	/**
	 * Holt sich alle vorhandenen Spieler und Strafen von der Datenhaltung und
	 * gibt diese als Arraylist zurück
	 * 
	 * @param context
	 *            Die Activity, welche die Methode aufruft
	 * @param messenger
	 *            Der Messenger über den Daten zurück zu Activity gesendet
	 *            werden
	 */
	public void getAllSpielerAndStrafen(Context context, Messenger messenger);

	/**
	 * Holt sich alle vorhandenen Spieler von der Datenhaltung und gibt diese
	 * als Arraylist zurück
	 * 
	 * @param context
	 *            Die Activity, welche die Methode aufruft
	 * @param messenger
	 *            Der Messenger über den Daten zurück zu Activity gesendet
	 *            werden
	 */
	public void getAllSpieler(Context context, Messenger messenger);

	/**
	 * Gibt alle Strafen für den übergebenen Spieler zurück
	 * 
	 * @param spieler
	 * @return Alle Strafen des Spielers als Array
	 */
	public Strafe[] getStrafenFor(String vorname, String nachname);

	/**
	 * Ruft eine PHP-Datei auf um einen neuen Spieler mit den übergebenen
	 * Parametern anzulegen
	 * 
	 * @param messenger
	 *            Der Messenger über den Daten zurück zu Activity gesendet
	 *            werden
	 * @param vorname
	 *            Der Vorname des Spielers
	 * @param nachname
	 *            Der Nachname des Spielers
	 */
	public void spielerAnlegen(Messenger messenger, String vorname,
			String nachname);

	/**
	 * Ruft eine PHP-Datei auf um eine neue Strafe mit den übergebenen
	 * Parametern anzulegen
	 * 
	 * @param messenger
	 *            Der Messenger über den Daten zurück zu Activity gesendet
	 *            werden
	 * @param beschreibung
	 *            Die Beschreibung der Strafe
	 * @param preis
	 *            Der Preis der Strafe
	 */
	public void strafeAnlegen(Messenger messenger, String beschreibung,
			String preis);

	/**
	 * Holt sich alle vorhandenen Strafen von der Datenhaltung und gibt sie als
	 * Araylist zurück
	 * 
	 * @return eine Arraylist mit allen Strafen
	 */
	public ArrayList<Strafe> getAllStrafen();

	/**
	 * Ruft eine PHP-Datei auf um ein neues Vergehen mit den übergebenen
	 * Parametern anzulegen
	 * 
	 * @param messenger
	 *            Der Messenger über den Daten zurück zu Activity gesendet
	 *            werden
	 * @param spieler
	 *            Der Spieler zu dem das Vergehen angelegt werden soll
	 * @param strafe
	 *            Die Strafe, die der Spieler begangen hat
	 * @param datum
	 *            Das Datum an dem die Strafe begangen wurde
	 */
	public void vergehenAnlegen(Messenger messenger, Spieler spieler,
			Strafe strafe, String datum);
}
