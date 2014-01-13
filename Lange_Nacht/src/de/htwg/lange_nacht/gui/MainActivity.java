package de.htwg.lange_nacht.gui;

import de.htwg.lange_nacht.R;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class MainActivity extends Activity {

	private Button btnStrafeEintragen;
	private Button btnStrafeAnlegen;
	private Button btnSpielerAuswaehlen;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		btnStrafeEintragen = (Button) findViewById(R.id.btnStrafeEintragen);
		btnStrafeAnlegen = (Button) findViewById(R.id.btnStrafeAnlegen);
		btnSpielerAuswaehlen = (Button) findViewById(R.id.btnSpielerAuswaehlen);

		// Festlegen was beim Klick auf Strafe eintragen passiert
		btnStrafeEintragen.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				Intent geheZuStrafeEintragen = new Intent(MainActivity.this,
						StrafeEintragenActivity.class);
				startActivity(geheZuStrafeEintragen);
			}
		});

		// Festlegen was beim Klick auf Strafe anlegen passiert
		btnStrafeAnlegen.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				Intent geheZuStrafeAnlegen = new Intent(MainActivity.this,
						StrafeAnlegenActivity.class);
				startActivity(geheZuStrafeAnlegen);
			}
		});

		// Festlegen was beim Klick auf Spieler auswählen passiert
		btnSpielerAuswaehlen.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				Intent geheZuSpielerAuswaehlen = new Intent(MainActivity.this,
						SpielerAuswaehlenActivity.class);
				startActivity(geheZuSpielerAuswaehlen);
			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
