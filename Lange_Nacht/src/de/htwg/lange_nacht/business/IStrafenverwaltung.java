package de.htwg.lange_nacht.business;

import java.util.ArrayList;

import de.htwg.lange_nacht.data.Spieler;
import de.htwg.lange_nacht.data.Strafe;

public interface IStrafenverwaltung {

	/**
	 * Holt sich alle vorhandenen Spieler von der Datenhaltung und gibt diese
	 * als Arraylist zur�ck
	 * 
	 * @return ein String Array mit allen Spielern
	 */
	public ArrayList<Spieler> getAllSpieler();

	/**
	 * Holt sich alle vorhandenen Strafen von der Datenhaltung und gibt deren
	 * Beschreibungen als String-Array zur�ck
	 * 
	 * @return ein String Array mit den Beschreibungen der Strafen
	 */
	public String[] getAllBeschreibungen();

	/**
	 * Holt sich von der Datenhaltung den entsprechenden Preis zu der Strafe mit
	 * der �bergebenen Beschreibung und gibt diesen int_wert zur�ck
	 * 
	 * @param beschreibung
	 * @return ein int-Wert mit dem Preis zur Strafe
	 */
	public int getPreisFor(String beschreibung);

	/**
	 * Gibt alle Strafen f�r den �bergebenen Spieler zur�ck
	 * @param spieler
	 * @return Alle Strafen des Spielers als Array
	 */
	public Strafe[] getStrafenFor(String vorname, String nachname);

	/**
	 * Ruft eine PHP-Datei auf um einen neuen Spieler mit den �bergebenen Parametern anzulegen
	 * @param vorname
	 * @param nachname
	 */
	public void spielerAnlegen(String vorname, String nachname);
	
	/**
	 * Ruft eine PHP-Datei auf um eine neue Strafe mit den �bergebenen Parametern anzulegen
	 * @param beschreibung
	 * @param preis
	 */
	public void strafeAnlegen(String beschreibung, String preis);
}
