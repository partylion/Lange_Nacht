package de.htwg.lange_nacht.data;

public class Spieler {

	private String vorname;
	private String nachname;

	public Spieler(String vorname, String nachname) {
		this.vorname = vorname;
		this.nachname = nachname;
	}

	public String getVorname() {
		return vorname;
	}

	public String getNachname() {
		return nachname;
	}
}
