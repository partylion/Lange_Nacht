package de.htwg.lange_nacht.gui;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import de.htwg.lange_nacht.R;
import de.htwg.lange_nacht.business.Strafenverwaltung;

public class StrafeEintragenActivity extends Activity {

	private TextView txtViewPreis;
	private Spinner spinnerSpieler;
	private Spinner spinnerStrafen;
	private Strafenverwaltung strafenverwaltungsinstanz = Strafenverwaltung
			.getInstance();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_strafe_eintragen);

		txtViewPreis = (TextView) findViewById(R.id.txtViewPreis);
		spinnerSpieler = (Spinner) findViewById(R.id.spinnerSpieler);
		spinnerStrafen = (Spinner) findViewById(R.id.spinnerStrafen);

		String[] beschreibungen = strafenverwaltungsinstanz
				.getAllBeschreibungen();
		String[] spieler = strafenverwaltungsinstanz.getAllSpieler();

		// Die beiden Spinner mit den Daten befüllen
		ArrayAdapter<String> adapterStrafen = new ArrayAdapter<String>(this,
				android.R.layout.simple_spinner_item, beschreibungen);
		// Specify the layout to use when the list of choices appears
		adapterStrafen
				.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		// Apply the adapter to the spinner
		spinnerStrafen.setAdapter(adapterStrafen);

		ArrayAdapter<String> adapterSpieler = new ArrayAdapter<String>(this,
				android.R.layout.simple_spinner_item, spieler);
		// Specify the layout to use when the list of choices appears
		adapterSpieler
				.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		// Apply the adapter to the spinner
		spinnerSpieler.setAdapter(adapterSpieler);

		// Ändert den Inhalt von txtViewPreis entsprechend der ausgewählten
		// Strafe
		spinnerStrafen.setOnItemSelectedListener(new OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> parentView,
					View selectedItemView, int position, long id) {

				String selectedItem = (String) spinnerStrafen.getSelectedItem();

				if (!(selectedItem.equals("Strafe auswählen"))) {
					// TODO Verhalten beim Auswählen einer Strafe festlegen
					txtViewPreis.setTextColor(Color.RED);
					txtViewPreis.setText("25 €");

					// int preis = Strafenverwaltung.getPreisFor(selectedItem);
					// txtViewPreis.setText(preis);
				}

			}

			@Override
			public void onNothingSelected(AdapterView<?> parentView) {
				// your code here
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
