package de.htwg.lange_nacht.data;

public class Strafe {

	private String beschreibung;
	private int preis;
	private String id;
	
	public Strafe(String beschreibung, int preis){
		this.beschreibung=beschreibung;
		this.preis=preis;
	}

	public Strafe(String id, String beschreibung, int preis) {
		super();
		this.beschreibung = beschreibung;
		this.preis = preis;
		this.id = id;
	}
	
	public String getBeschreibung(){
		return beschreibung;
	}
	
	public int getPreis(){
		return preis;
	}
	
	public String getID() {
		return id;
	}	
	
	@Override
	public String toString(){
		return beschreibung+" - "+preis+" €";
	}
}
