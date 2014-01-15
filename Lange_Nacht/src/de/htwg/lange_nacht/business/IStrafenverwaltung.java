package de.htwg.lange_nacht.business;

import java.util.ArrayList;

import de.htwg.lange_nacht.data.Spieler;
import de.htwg.lange_nacht.data.Strafe;

public interface IStrafenverwaltung {

	/**
	 * Holt sich alle vorhandenen Spieler von der Datenhaltung und gibt diese
	 * als Arraylist zurück
	 * 
	 * @return ein String Array mit allen Spielern
	 */
	public ArrayList<Spieler> getAllSpieler();

	/**
	 * Holt sich alle vorhandenen Strafen von der Datenhaltung und gibt deren
	 * Beschreibungen als String-Array zurück
	 * 
	 * @return ein String Array mit den Beschreibungen der Strafen
	 */
	public String[] getAllBeschreibungen();

	/**
	 * Holt sich von der Datenhaltung den entsprechenden Preis zu der Strafe mit
	 * der übergebenen Beschreibung und gibt diesen int_wert zurück
	 * 
	 * @param beschreibung
	 * @return ein int-Wert mit dem Preis zur Strafe
	 */
	public int getPreisFor(String beschreibung);

	/**
	 * Gibt alle Strafen für den übergebenen Spieler zurück
	 * @param spieler
	 * @return Alle Strafen des Spielers als Array
	 */
	public Strafe[] getStrafenFor(String vorname, String nachname);

	/**
	 * Ruft eine PHP-Datei auf um einen neuen Spieler mit den übergebenen Parametern anzulegen
	 * @param vorname
	 * @param nachname
	 */
	public void spielerAnlegen(String vorname, String nachname);
	
	/**
	 * Ruft eine PHP-Datei auf um eine neue Strafe mit den übergebenen Parametern anzulegen
	 * @param beschreibung
	 * @param preis
	 */
	public void strafeAnlegen(String beschreibung, String preis);
}
