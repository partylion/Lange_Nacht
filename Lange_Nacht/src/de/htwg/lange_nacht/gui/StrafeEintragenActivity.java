package de.htwg.lange_nacht.gui;

import java.util.ArrayList;
import java.util.Calendar;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog.OnDateSetListener;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.Messenger;
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
import de.htwg.lange_nacht.data.Messages;
import de.htwg.lange_nacht.data.Spieler;
import de.htwg.lange_nacht.data.Strafe;

@SuppressLint("HandlerLeak")
public class StrafeEintragenActivity extends FragmentActivity {

	private TextView txtViewPreis;
	private Spinner spinnerSpieler;
	private Spinner spinnerStrafen;
	private Strafenverwaltung strafenverwaltungsinstanz = Strafenverwaltung
			.getInstance();
	private Button btnStrafeEintragenSubmit;
	private Button btnDatumwaehlen;
	private int selectedYear = 0, selectedMonth = 0, selectedDay = 0;
	static final int DATE_DIALOG_ID = 0;

	private ArrayAdapter<String> adapterSpieler;
	private Spieler[] spielerListe;
	private ArrayAdapter<String> adapterStrafen;
	private Strafe[] strafenListe;

	private Handler handler = new Handler() {
		public void handleMessage(Message message) {
			Object list = message.obj;
			if (message.arg1 == RESULT_OK && list != null
					&& message.arg2 == Messages.GET_SPIELER) {
				ArrayList<Spieler> alleSpieler = (ArrayList<Spieler>) list;
				adapterSpieler.clear();
				spielerListe = new Spieler[alleSpieler.size()];
				for (int i = 0; i < alleSpieler.size(); i++) {
					spielerListe[i] = alleSpieler.get(i);
					adapterSpieler.add(alleSpieler.get(i).getVorname() + " "
							+ alleSpieler.get(i).getNachname());
				}
				adapterSpieler.notifyDataSetChanged();
			} else if (message.arg1 == RESULT_OK && list != null
					&& message.arg2 == Messages.GET_STRAFEN) {
				ArrayList<Strafe> alleStrafen = (ArrayList<Strafe>) list;
				adapterStrafen.clear();
				strafenListe = new Strafe[alleStrafen.size()];
				for (int i = 0; i < alleStrafen.size(); i++) {
					strafenListe[i] = alleStrafen.get(i);
					adapterStrafen.add(alleStrafen.get(i).getBeschreibung());
				}
				adapterStrafen.notifyDataSetChanged();
			} else if (message.arg1 == RESULT_OK
					&& message.arg2 == Messages.SET_VERGEHEN) {
				Toast.makeText(StrafeEintragenActivity.this,
						"Eintrag erfolgreich", Toast.LENGTH_LONG).show();
			}

			else {
				Toast.makeText(StrafeEintragenActivity.this,
						"Download failed.", Toast.LENGTH_LONG).show();
			}
		};
	};

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

		strafenverwaltungsinstanz.getAllSpielerAndStrafen(this, new Messenger(
				handler));

		// Die beiden Spinner mit den Daten befüllen
		// Specify the layout to use when the list of choices appears
		adapterStrafen = new ArrayAdapter<String>(this,
				android.R.layout.simple_spinner_item);
		adapterStrafen
				.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		// Apply the adapter to the spinner
		spinnerStrafen.setAdapter(adapterStrafen);
		// Specify the layout to use when the list of choices appears
		adapterSpieler = new ArrayAdapter<String>(this,
				android.R.layout.simple_spinner_item);
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
				for (int i = 0; i < strafenListe.length; i++) {
					if (selectedItem.equals(strafenListe[i].getBeschreibung())) {
						preis = strafenListe[i].getPreis();
					}
				}
				txtViewPreis.setText(preis + " €");

			}

			@Override
			public void onNothingSelected(AdapterView<?> parentView) {
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

				for (int i = 0; i < strafenListe.length; i++) {
					if (selectedStrafe.equals(strafenListe[i].getBeschreibung())) {
						strafe = strafenListe[i];
					}
				}

				for (int i = 0; i < spielerListe.length; i++) {
					if (selectedSpieler.equals(spielerListe[i].getVorname()
							+ " " + spielerListe[i].getNachname())) {
						spieler = spielerListe[i];
					}
				}

				if (spieler != null && strafe != null && selectedDay != 0
						&& selectedMonth != 0 && selectedYear != 0) {
					// Funktion der Strafenverwaltung aufrufen, die das Vergehen
					// abspeichert
					strafenverwaltungsinstanz.vergehenAnlegen(new Messenger(
							handler), spieler, strafe, selectedYear + "-"
							+ selectedMonth + "-" + selectedDay);

					// Zur MainActivity wechseln
					Intent geheZuMain = new Intent(
							StrafeEintragenActivity.this, MainActivity.class);
					startActivity(geheZuMain);
				} else {
					Toast.makeText(StrafeEintragenActivity.this, "Bitte alle Felder ausfüllen",
							Toast.LENGTH_LONG).show();
				}
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
