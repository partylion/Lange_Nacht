package de.htwg.lange_nacht.gui;

import java.util.ArrayList;

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
import de.htwg.lange_nacht.data.Spieler;
import de.htwg.lange_nacht.data.Strafe;

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
		
		final ArrayList<Strafe> alleStrafen = strafenverwaltungsinstanz.getAllStrafen();
		ArrayList<Spieler> alleSpieler = strafenverwaltungsinstanz.getAllSpieler();
		
		String[] spielerliste = new String[alleSpieler.size()+1];
		spielerliste[0] = "Spieler auswählen";
		for (int i = 1; i < spielerliste.length; i++) {
			spielerliste[i]=alleSpieler.get(i-1).getVorname()+" "+alleSpieler.get(i-1).getNachname();
		}
		
		String[] strafenliste = new String[alleStrafen.size()+1];
		strafenliste[0] = "Strafe auswählen";
		for (int i = 1; i < strafenliste.length; i++) {
			strafenliste[i]=alleStrafen.get(i-1).getBeschreibung();
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

				if (selectedItem.equals("Strafe auswählen")) {
					// TODO Verhalten beim Auswählen einer Strafe festlegen	
					txtViewPreis.setText("");
				} else {
					int preis = 0;
					for (int i = 0; i < alleStrafen.size(); i++) {
						if(selectedItem.equals(alleStrafen.get(i).getBeschreibung())){
							preis=alleStrafen.get(i).getPreis();
						}
					}
					System.out.println("ich lebe noch");
					txtViewPreis.setText(preis+" €");
					System.out.println("ich lebe immer noch");
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
