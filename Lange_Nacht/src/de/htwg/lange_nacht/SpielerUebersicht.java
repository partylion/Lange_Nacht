package de.htwg.lange_nacht;

import de.htwg.lange_nacht.business.Strafenverwaltung;
import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.widget.TextView;

public class SpielerUebersicht extends Activity {
	
	private TextView txtViewSpielerName;
	private String spielerName = getIntent().getExtras().getString("Spielername");
//	private Strafenverwaltung strafenverwaltungsinstanz = Strafenverwaltung
//			.getInstance();

	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_spieler_uebersicht);
		
		txtViewSpielerName = (TextView) findViewById(R.id.txtViewSpielerName);
		txtViewSpielerName.setText(spielerName);
		
		//TODO Information zum Spieler holen und übersichtlich darstellen
//		strafenverwaltungsinstanz.getStrafenFor(spielerName);
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.spieler_uebersicht, menu);
		return true;
	}

}
