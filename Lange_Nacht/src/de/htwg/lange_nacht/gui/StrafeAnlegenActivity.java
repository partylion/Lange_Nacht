package de.htwg.lange_nacht.gui;

import de.htwg.lange_nacht.R;
import de.htwg.lange_nacht.business.Strafenverwaltung;
import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

public class StrafeAnlegenActivity extends Activity {

	private Button btnStrafeAnlegenSubmit;
	private EditText editTextBeschreibung;
	private EditText editTextPreis;
	private Strafenverwaltung strafenverwaltunginstanz = Strafenverwaltung.getInstance();
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_strafe_anlegen);
		
		editTextBeschreibung = (EditText) findViewById(R.id.editTextStrafenName);
		editTextPreis = (EditText) findViewById(R.id.editTextStrafePreis);
		btnStrafeAnlegenSubmit = (Button) findViewById(R.id.btnStrafeAnlegenSubmit);

		// Festlegen was beim Klick auf Neue Strafe anlegen passiert
		btnStrafeAnlegenSubmit.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				String beschreibung = editTextBeschreibung.getText().toString().trim();
				String preis = editTextPreis.getText().toString().trim();
				
				strafenverwaltunginstanz.strafeAnlegen(beschreibung, preis);
				
		        Intent geheZuMain = new Intent(StrafeAnlegenActivity.this,
						MainActivity.class);
				startActivity(geheZuMain);
			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.strafe_anlegen, menu);
		return true;
	}

}
