package de.htwg.lange_nacht.business;

import android.content.Context;
import android.os.Messenger;
import de.htwg.lange_nacht.data.Spieler;
import de.htwg.lange_nacht.data.Strafe;

public interface IStrafenverwaltung {

	/**
	 * Holt sich alle vorhandenen Spieler und Strafen von der Datenhaltung
	 * 
	 * @param context
	 *            Die Activity, welche die Methode aufruft
	 * @param messenger
	 *            Der Messenger �ber den Daten zur�ck zu Activity gesendet
	 *            werden
	 */
	public void getAllSpielerAndStrafen(Context context, Messenger messenger);

	/**
	 * Holt sich alle vorhandenen Spieler von der Datenhaltung und gibt diese
	 * als Arraylist zur�ck
	 * 
	 * @param context
	 *            Die Activity, welche die Methode aufruft
	 * @param messenger
	 *            Der Messenger �ber den Daten zur�ck zu Activity gesendet
	 *            werden
	 */
	public void getAllSpieler(Context context, Messenger messenger);

	/**
	 * Ruft einen AsyncTask auf, der alle Strafen f�r den Spieler vom Server
	 * holt
	 * 
	 * @param context
	 *            Die Activity, welche die Methode aufruft
	 * @param messenger
	 *            Der Messenger �ber den Daten zur�ck zu Activity gesendet
	 *            werden
	 * @param vorname
	 *            Der Vorname des Spielers zu dem die Strafen geholt werden
	 *            sollen
	 * @param nachname
	 *            Der Nachname des Spielers zu dem die Strafen geholt werden
	 *            sollen
	 */
	public void getAllStrafenFor(Context context, Messenger messenger,
			String vorname, String nachname);

	/**
	 * Ruft einen AsyncTask auf, der alle offene Strafen f�r den Spieler vom
	 * Server holt
	 * 
	 * @param context
	 *            Die Activity, welche die Methode aufruft
	 * @param messenger
	 *            Der Messenger �ber den Daten zur�ck zu Activity gesendet
	 *            werden
	 * @param vorname
	 *            Der Vorname des Spielers zu dem die Strafen geholt werden
	 *            sollen
	 * @param nachname
	 *            Der Nachname des Spielers zu dem die Strafen geholt werden
	 *            sollen
	 */
	public void getOffeneStrafenFor(Context context, Messenger messenger,
			String vorname, String nachname);

	/**
	 * Ruft einen AsyncTask auf, der alle bezahlten Strafen f�r den Spieler vom
	 * Server holt
	 * 
	 * @param context
	 *            Die Activity, welche die Methode aufruft
	 * @param messenger
	 *            Der Messenger �ber den Daten zur�ck zu Activity gesendet
	 *            werden
	 * @param vorname
	 *            Der Vorname des Spielers zu dem die Strafen geholt werden
	 *            sollen
	 * @param nachname
	 *            Der Nachname des Spielers zu dem die Strafen geholt werden
	 *            sollen
	 */
	public void getBezahlteStrafenFor(Context context, Messenger messenger,
			String vorname, String nachname);

	/**
	 * Ruft eine PHP-Datei auf um einen neuen Spieler mit den �bergebenen
	 * Parametern anzulegen
	 * 
	 * @param messenger
	 *            Der Messenger �ber den Daten zur�ck zu Activity gesendet
	 *            werden
	 * @param vorname
	 *            Der Vorname des Spielers
	 * @param nachname
	 *            Der Nachname des Spielers
	 */
	public void spielerAnlegen(Messenger messenger, String vorname,
			String nachname);

	/**
	 * Ruft eine PHP-Datei auf um eine neue Strafe mit den �bergebenen
	 * Parametern anzulegen
	 * 
	 * @param messenger
	 *            Der Messenger �ber den Daten zur�ck zu Activity gesendet
	 *            werden
	 * @param beschreibung
	 *            Die Beschreibung der Strafe
	 * @param preis
	 *            Der Preis der Strafe
	 */
	public void strafeAnlegen(Messenger messenger, String beschreibung,
			String preis);

	/**
	 * Ruft eine PHP-Datei auf um ein neues Vergehen mit den �bergebenen
	 * Parametern anzulegen
	 * 
	 * @param messenger
	 *            Der Messenger �ber den Daten zur�ck zu Activity gesendet
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

	/**
	 * Ruft eine PHP-Datei auf, die der Strafe das aktuelle Datum als
	 * Bezahldatum �bergibt
	 * 
	 * @param strafe
	 *            Die Strafe die bezahlt wurde
	 */
	public void setBezahlt(Messenger messenger, Strafe strafe, String vorname,
			String nachname);
}
