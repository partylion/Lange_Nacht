package de.htwg.lange_nacht.gui;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.widget.TextView;
import de.htwg.lange_nacht.R;
import de.htwg.lange_nacht.business.Strafenverwaltung;

public class SpielerUebersicht extends Activity {
	
	private TextView txtViewSpielerName;
	private Strafenverwaltung strafenverwaltungsinstanz = Strafenverwaltung
			.getInstance();

	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_spieler_uebersicht);
		
		String spielerName = getIntent().getExtras().getString("Spielername");
		
		txtViewSpielerName = (TextView) findViewById(R.id.txtViewSpielerName);
		txtViewSpielerName.setText(spielerName);
		
		String[] name = spielerName.split("\\s");
		String vorname = name[0];
		String nachname = name[1];

		
		//TODO Information zum Spieler holen und übersichtlich darstellen
		strafenverwaltungsinstanz.getStrafenFor(vorname, nachname);
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.spieler_uebersicht, menu);
		return true;
	}

}
