package de.htwg.lange_nacht.business;



public class Strafenverwaltung {

	private String[] dummyBeschreibungen = { "Strafe auswählen", "Strafe1",
			"Strafe2", "Strafe3", "Strafe4", "Strafe5" };
	private String[] dummySpieler = { "Spieler auswählen", "Spieler1",
			"Spieler2", "Spieler3", "Spieler4", "Spieler5" };
	
	protected Strafenverwaltung(){
		
	}
	
	private static Strafenverwaltung strafenverwaltungsinstanz = new Strafenverwaltung();
	
	/**
	 * Holt sich alle vorhandenen Spieler von der Datenhaltung und gibt diese
	 * als String-Array zurück
	 * 
	 * @return ein String Array mit allen Spielern
	 */
	public String[] getAllSpieler() {
		// TODO Logik implementieren
		return dummySpieler;
	}
	
	/**
	 * Gibt das einzige Objekt der Klasse Strafenverwaltung zurück, das erzeugt werden darf
	 * 
	 * @return das einzige instanzierte Objekt der Klasse
	 */
	public static Strafenverwaltung getInstance()
	{
		return strafenverwaltungsinstanz;
	}

	/**
	 * Holt sich alle vorhandenen Strafen von der Datenhaltung und gibt deren
	 * Beschreibungen als String-Array zurück
	 * 
	 * @return ein String Array mit den Beschreibungen der Strafen
	 */
	public String[] getAllBeschreibungen() {
		// TODO Logik implementieren
		return dummyBeschreibungen;
	}

	/**
	 * Holt sich von der Datenhaltung den entsprechenden Preis zu der Strafe mit
	 * der übergebenen Beschreibung und gibt diesen int_wert zurück
	 * 
	 * @param beschreibung
	 * @return ein int-Wert mit dem Preis zur Strafe
	 */
	public int getPreisFor(String beschreibung) {

		int preis = 0;
		
		//TODO Loki einbauen
		
		return preis;
	}
}
