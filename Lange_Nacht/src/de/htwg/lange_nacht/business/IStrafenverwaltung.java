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
	 * @param vorname
	 * @param nachname
	 */
	public void spielerAnlegen(String vorname, String nachname);

	/**
	 * Ruft eine PHP-Datei auf um eine neue Strafe mit den übergebenen
	 * Parametern anzulegen
	 * 
	 * @param beschreibung
	 * @param preis
	 */
	public void strafeAnlegen(String beschreibung, String preis);

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
	 * @param spieler
	 * @param strafe
	 */
	public void vergehenAnlegen(Spieler spieler, Strafe strafe, String datum);
}
