package de.htwg.lange_nacht;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class MainActivity extends Activity {

	private Button btn_strafe_eintragen;
	private Button btn_strafe_anlegen;
	private Button btn_spieler_auswaehlen;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		btn_strafe_eintragen = (Button) findViewById(R.id.btn_strafe_eintragen);
		btn_strafe_anlegen = (Button) findViewById(R.id.btn_strafe_anlegen);
		btn_spieler_auswaehlen = (Button) findViewById(R.id.btn_spieler_auswaehlen);

		// Festlegen was beim Klick auf Strafe eintragen passiert
		btn_strafe_eintragen.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				Intent geh_zu_strafe_eintragen = new Intent(MainActivity.this,
						StrafeEintragenActivity.class);
				startActivity(geh_zu_strafe_eintragen);
			}
		});

		// Festlegen was beim Klick auf Strafe anlegen passiert
		btn_strafe_anlegen.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				Intent geh_zu_strafe_anlegen = new Intent(MainActivity.this,
						StrafeAnlegenActivity.class);
				startActivity(geh_zu_strafe_anlegen);
			}
		});

		// Festlegen was beim Klick auf Spieler auswählen passiert
		btn_spieler_auswaehlen.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				Intent geh_zu_spieler_auswaehlen = new Intent(MainActivity.this,
						SpielerAuswaehlenActivity.class);
				startActivity(geh_zu_spieler_auswaehlen);
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
