package de.htwg.lange_nacht.business;

import de.htwg.lange_nacht.data.Strafe;



public class Strafenverwaltung {

	private String[] dummyBeschreibungen = { "Strafe ausw�hlen", "Strafe1",
			"Strafe2", "Strafe3", "Strafe4", "Strafe5" };
	private String[] dummySpieler = { "Spieler ausw�hlen", "Spieler1",
			"Spieler2", "Spieler3", "Spieler4", "Spieler5" };
	
	private Strafe[] dummyStrafenerzeugen(){
		
		Strafe strafe1 = new Strafe("Strafe1", 20);
		Strafe strafe2 = new Strafe("Strafe2", 50);
		Strafe strafe3 = new Strafe("Strafe1", 20);
		
		Strafe[] dummyStrafen = {strafe1, strafe2, strafe3};
		
		return dummyStrafen;
	}
	
	protected Strafenverwaltung(){
		
	}
	
	private static Strafenverwaltung strafenverwaltungsinstanz = new Strafenverwaltung();
	
	/**
	 * Holt sich alle vorhandenen Spieler von der Datenhaltung und gibt diese
	 * als String-Array zur�ck
	 * 
	 * @return ein String Array mit allen Spielern
	 */
	public String[] getAllSpieler() {
		// TODO Logik implementieren
		return dummySpieler;
	}
	
	/**
	 * Gibt das einzige Objekt der Klasse Strafenverwaltung zur�ck, das erzeugt werden darf
	 * 
	 * @return das einzige instanzierte Objekt der Klasse
	 */
	public static Strafenverwaltung getInstance()
	{
		return strafenverwaltungsinstanz;
	}

	/**
	 * Holt sich alle vorhandenen Strafen von der Datenhaltung und gibt deren
	 * Beschreibungen als String-Array zur�ck
	 * 
	 * @return ein String Array mit den Beschreibungen der Strafen
	 */
	public String[] getAllBeschreibungen() {
		// TODO Logik implementieren
		return dummyBeschreibungen;
	}

	/**
	 * Holt sich von der Datenhaltung den entsprechenden Preis zu der Strafe mit
	 * der �bergebenen Beschreibung und gibt diesen int_wert zur�ck
	 * 
	 * @param beschreibung
	 * @return ein int-Wert mit dem Preis zur Strafe
	 */
	public int getPreisFor(String beschreibung) {

		int preis = 0;
		
		//TODO Logik einbauen
		
		return preis;
	}
	
	public Strafe[] getStrafenFor(String spieler){
		//TODO Strafen f�r Spieler aus Datenhaltung holen
		return dummyStrafenerzeugen();
	}
}
