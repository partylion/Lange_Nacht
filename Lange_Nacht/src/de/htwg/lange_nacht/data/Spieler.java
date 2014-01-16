package de.htwg.lange_nacht.data;

public class Spieler {

	private String vorname;
	private String nachname;
	private String id;

	public Spieler(String vorname, String nachname) {
		this.vorname = vorname;
		this.nachname = nachname;
	}
	
	public Spieler(String id, String vorname, String nachname) {
		this.id = id;
		this.vorname = vorname;
		this.nachname = nachname;
	}

	public String getID() {
		return id;
	}
	
	public String getVorname() {
		return vorname;
	}

	public String getNachname() {
		return nachname;
	}
}
