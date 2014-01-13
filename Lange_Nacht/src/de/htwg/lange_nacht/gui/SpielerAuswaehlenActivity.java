package de.htwg.lange_nacht.gui;

import de.htwg.lange_nacht.R;
import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;

public class SpielerAuswaehlenActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_spieler_auswaehlen);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.spieler_auswaehlen, menu);
		return true;
	}

}
