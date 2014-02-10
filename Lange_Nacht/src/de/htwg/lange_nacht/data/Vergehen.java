package de.htwg.lange_nacht.data;

public class Vergehen {

	private String beschreibung;
	private int preis;
	private int strafenId;
	private String vorname;
	private String nachname;
	private int spielerId;
	private String datum;

	public Vergehen(String beschreibung, int preis, int strafenId,
			String vorname, String nachname, int spielerId, String datum) {
		this.datum = datum;
		this.beschreibung = beschreibung;
		this.preis = preis;
		this.strafenId = strafenId;
		this.vorname = vorname;
		this.nachname = nachname;
		this.spielerId = spielerId;
	}

	public String getBeschreibung() {
		return beschreibung;
	}

	public String getDatum() {
		return datum;
	}

	public int getPreis() {
		return preis;
	}

	public int getStrafenId() {
		return strafenId;
	}

	public String getVorname() {
		return vorname;
	}

	public String getNachname() {
		return nachname;
	}

	public int getSpielerId() {
		return spielerId;
	}

	@Override
	public String toString() {
		return vorname + " " + nachname + " - " + beschreibung + " - " + preis
				+ " € - "+datum;
	}
}
