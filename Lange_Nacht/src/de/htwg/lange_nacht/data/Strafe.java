package de.htwg.lange_nacht.data;

public class Strafe {

	private String beschreibung;
	private int preis;
	
	public Strafe(String beschreibung, int preis){
		this.beschreibung=beschreibung;
		this.preis=preis;
	}
	
	public String getBeschreibung(){
		return beschreibung;
	}
	
	public int getPreis(){
		return preis;
	}
	
}
