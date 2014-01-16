package de.htwg.lange_nacht.gui;

import java.util.ArrayList;
import java.util.Calendar;

import android.app.DatePickerDialog.OnDateSetListener;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import de.htwg.lange_nacht.R;
import de.htwg.lange_nacht.business.Strafenverwaltung;
import de.htwg.lange_nacht.data.Spieler;
import de.htwg.lange_nacht.data.Strafe;

public class StrafeEintragenActivity extends FragmentActivity {

	private TextView txtViewPreis;
	private Spinner spinnerSpieler;
	private Spinner spinnerStrafen;
	private Strafenverwaltung strafenverwaltungsinstanz = Strafenverwaltung
			.getInstance();
	private Button btnStrafeEintragenSubmit;
	private Button btnDatumwaehlen;
	private int selectedYear, selectedMonth, selectedDay;
	private Strafenverwaltung strafenverwaltunginstanz = Strafenverwaltung
			.getInstance();
	static final int DATE_DIALOG_ID = 0;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_strafe_eintragen);

		// Referenzen holen
		txtViewPreis = (TextView) findViewById(R.id.txtViewPreis);
		spinnerSpieler = (Spinner) findViewById(R.id.spinnerSpieler);
		spinnerStrafen = (Spinner) findViewById(R.id.spinnerStrafen);
		btnStrafeEintragenSubmit = (Button) findViewById(R.id.btnStrafeEintragenSubmit);
		btnDatumwaehlen = (Button) findViewById(R.id.btnDatumwaehlen);

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

		// Festlegen was beim klick auf Strafe Eintragen passiert
		btnStrafeEintragenSubmit.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				// Ausgewählte Elemente aus den Spinnern holen und die passende
				// Strafe/Spieler dazu suchen
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

				// Funktion der Strafenverwaltung aufrufen, die das Vergehen
				// abspeichert
				strafenverwaltunginstanz.vergehenAnlegen(spieler, strafe,
						selectedYear + "-" + selectedMonth + "-" + selectedDay);

				// Benachrichtigung anzeigen
				Toast.makeText(StrafeEintragenActivity.this,
						"Strafe eingetragen", Toast.LENGTH_LONG).show();

				// Zur MainActivity wechseln
				Intent geheZuMain = new Intent(StrafeEintragenActivity.this,
						MainActivity.class);
				startActivity(geheZuMain);
			}
		});

		// Wenn im DatePickerDialog ein Datum ausgewählt wurde wird hier ein
		// Text erzeugt, der Text des btnDatumAuswaehlen geändert und die
		// Auswahl in die dazugehörigen Variablen gespeichert
		final OnDateSetListener ondate = new OnDateSetListener() {
			@Override
			public void onDateSet(DatePicker view, int year, int monthOfYear,
					int dayOfMonth) {
				Toast.makeText(
						StrafeEintragenActivity.this,
						String.valueOf(dayOfMonth) + "."
								+ String.valueOf(monthOfYear + 1) + "."
								+ String.valueOf(year), Toast.LENGTH_LONG)
						.show();
				selectedYear = year;
				selectedMonth = monthOfYear + 1;
				selectedDay = dayOfMonth;

				btnDatumwaehlen.setText(selectedDay + "." + selectedMonth + "."
						+ selectedYear);
			}
		};

		// Festlegen was beim klick auf Datum Auswählen passiert
		btnDatumwaehlen.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// DatePickerDialog anzeigen
				DatePickerFragment date = new DatePickerFragment();

				// aktuelles Datum setzten
				Calendar calender = Calendar.getInstance();
				Bundle args = new Bundle();
				args.putInt("year", calender.get(Calendar.YEAR));
				args.putInt("month", calender.get(Calendar.MONTH));
				args.putInt("day", calender.get(Calendar.DAY_OF_MONTH));
				date.setArguments(args);

				// Callback um eingegebenes Datum zu sichern
				date.setCallBack(ondate);
				date.show(getSupportFragmentManager(), "Date Picker");
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
