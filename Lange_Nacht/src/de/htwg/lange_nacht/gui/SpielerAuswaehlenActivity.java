package de.htwg.lange_nacht.gui;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.Messenger;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;
import de.htwg.lange_nacht.R;
import de.htwg.lange_nacht.business.Strafenverwaltung;
import de.htwg.lange_nacht.data.Messages;
import de.htwg.lange_nacht.data.Spieler;

public class SpielerAuswaehlenActivity extends Activity {

	private Spinner spinnerSpielerAuswaehlen;
	private Button btnSpielerAuswaehlenSubmit;
	private Strafenverwaltung strafenverwaltungsinstanz = Strafenverwaltung
			.getInstance();
	private ArrayAdapter<String> adapterSpieler;
	private Spieler[] spielerListe;

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
			} else {
				Toast.makeText(SpielerAuswaehlenActivity.this,
						"Download failed.", Toast.LENGTH_LONG).show();
			}
		};
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_spieler_auswaehlen);

		spinnerSpielerAuswaehlen = (Spinner) findViewById(R.id.spinnerSpielerAuswaehlen);

		strafenverwaltungsinstanz.getAllSpieler(this, new Messenger(
				handler));

		// Specify the layout to use when the list of choices appears
		adapterSpieler = new ArrayAdapter<String>(this,
				android.R.layout.simple_spinner_item);
		adapterSpieler
				.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		// Apply the adapter to the spinner
		spinnerSpielerAuswaehlen.setAdapter(adapterSpieler);

		btnSpielerAuswaehlenSubmit = (Button) findViewById(R.id.btnSpielerAuswaehlenSubmit);

		// Festlegen was beim Klick auf Spieler auswählen passiert
		btnSpielerAuswaehlenSubmit.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				String spieler = (String) spinnerSpielerAuswaehlen
						.getSelectedItem();
				Intent geheZuSpielerUebersicht = new Intent(
						SpielerAuswaehlenActivity.this, SpielerUebersicht.class);
				geheZuSpielerUebersicht.putExtra("Spielername", spieler);
				startActivity(geheZuSpielerUebersicht);
			}
		});

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.spieler_auswaehlen, menu);
		return true;
	}

}
