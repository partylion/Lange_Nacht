package de.htwg.lange_nacht.gui;

import java.util.ArrayList;
import java.util.Date;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import de.htwg.lange_nacht.R;
import de.htwg.lange_nacht.business.Strafenverwaltung;
import de.htwg.lange_nacht.data.Spieler;
import de.htwg.lange_nacht.data.Strafe;

public class StrafeEintragenActivity extends Activity {

	private TextView txtViewPreis;
	private Spinner spinnerSpieler;
	private Spinner spinnerStrafen;
	private Strafenverwaltung strafenverwaltungsinstanz = Strafenverwaltung
			.getInstance();
	private Button btnStrafeEintragenSubmit;
	private Strafenverwaltung strafenverwaltunginstanz = Strafenverwaltung
			.getInstance();
	private Date dummyDate = new java.util.Date();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_strafe_eintragen);

		txtViewPreis = (TextView) findViewById(R.id.txtViewPreis);
		spinnerSpieler = (Spinner) findViewById(R.id.spinnerSpieler);
		spinnerStrafen = (Spinner) findViewById(R.id.spinnerStrafen);
		btnStrafeEintragenSubmit = (Button) findViewById(R.id.btnStrafeEintragenSubmit);

		final ArrayList<Strafe> alleStrafen = strafenverwaltungsinstanz
				.getAllStrafen();
		final ArrayList<Spieler> alleSpieler = strafenverwaltungsinstanz
				.getAllSpieler();

		String[] spielerliste = new String[alleSpieler.size()];
		for (int i = 0; i < spielerliste.length; i++) {
			spielerliste[i] = alleSpieler.get(i).getVorname() + " "
					+ alleSpieler.get(i).getNachname();
		}

		String[] strafenliste = new String[alleStrafen.size()];
		for (int i = 0; i < strafenliste.length; i++) {
			strafenliste[i] = alleStrafen.get(i).getBeschreibung();
		}

		// Die beiden Spinner mit den Daten befüllen
		ArrayAdapter<String> adapterStrafen = new ArrayAdapter<String>(this,
				android.R.layout.simple_spinner_item, strafenliste);
		// Specify the layout to use when the list of choices appears
		adapterStrafen
				.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		// Apply the adapter to the spinner
		spinnerStrafen.setAdapter(adapterStrafen);

		ArrayAdapter<String> adapterSpieler = new ArrayAdapter<String>(this,
				android.R.layout.simple_spinner_item, spielerliste);
		// Specify the layout to use when the list of choices appears
		adapterSpieler
				.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		// Apply the adapter to the spinner
		spinnerSpieler.setAdapter(adapterSpieler);

		txtViewPreis.setTextColor(Color.RED);

		// Ändert den Inhalt von txtViewPreis entsprechend der ausgewählten
		// Strafe
		spinnerStrafen.setOnItemSelectedListener(new OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> parentView,
					View selectedItemView, int position, long id) {

				String selectedItem = (String) spinnerStrafen.getSelectedItem();

				int preis = 0;
				for (int i = 0; i < alleStrafen.size(); i++) {
					if (selectedItem.equals(alleStrafen.get(i)
							.getBeschreibung())) {
						preis = alleStrafen.get(i).getPreis();
					}
				}
				txtViewPreis.setText(preis + " €");

			}

			@Override
			public void onNothingSelected(AdapterView<?> parentView) {
				// your code here
			}

		});

		btnStrafeEintragenSubmit.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				String selectedStrafe = (String) spinnerStrafen
						.getSelectedItem();
				String selectedSpieler = (String) spinnerSpieler
						.getSelectedItem();

				Spieler spieler = null;
				Strafe strafe = null;

				for (int i = 0; i < alleStrafen.size(); i++) {
					if (selectedStrafe.equals(alleStrafen.get(i)
							.getBeschreibung())) {
						strafe = alleStrafen.get(i);
					}
				}

				for (int i = 0; i < alleSpieler.size(); i++) {
					if (selectedSpieler.equals(alleSpieler.get(i).getVorname()
							+ " " + alleSpieler.get(i).getNachname())) {
						spieler = alleSpieler.get(i);
					}
				}

				strafenverwaltunginstanz.vergehenAnlegen(spieler, strafe,
						dummyDate);

				Intent geheZuMain = new Intent(StrafeEintragenActivity.this,
						MainActivity.class);
				startActivity(geheZuMain);
			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.strafe_eintragen, menu);
		return true;
	}

}
