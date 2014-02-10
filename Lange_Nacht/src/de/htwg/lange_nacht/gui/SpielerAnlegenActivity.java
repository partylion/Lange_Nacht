package de.htwg.lange_nacht.gui;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.Messenger;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import de.htwg.lange_nacht.R;
import de.htwg.lange_nacht.business.Strafenverwaltung;
import de.htwg.lange_nacht.data.Messages;

public class SpielerAnlegenActivity extends Activity {

	private Button btnSpielerAnlegenSubmit;
	private EditText editTextNachname;
	private EditText editTextVorname;
	private Strafenverwaltung strafenverwaltunginstanz = Strafenverwaltung
			.getInstance();

	@SuppressLint("HandlerLeak")
	private Handler handler = new Handler() {
		public void handleMessage(Message message) {
			if (message.arg1 == RESULT_OK
					&& message.arg2 == Messages.SET_SPIELER) {
				Toast.makeText(SpielerAnlegenActivity.this, "Spieler angelegt",
						Toast.LENGTH_LONG).show();
			} else {
				Toast.makeText(SpielerAnlegenActivity.this, "Upload failed.",
						Toast.LENGTH_LONG).show();
			}
		};
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_spieler_anlegen);

		editTextNachname = (EditText) findViewById(R.id.editTextNachname);
		editTextVorname = (EditText) findViewById(R.id.editTextVorname);
		btnSpielerAnlegenSubmit = (Button) findViewById(R.id.btnSpielerAnlegenSubmit);

		// Festlegen was beim Klick auf Neuen Spieler anlegen passiert
		btnSpielerAnlegenSubmit.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				String vorname = editTextVorname.getText().toString().trim();
				String nachname = editTextNachname.getText().toString().trim();

				if (!(vorname.equals("")) && !(nachname.equals(""))) {
					strafenverwaltunginstanz.spielerAnlegen(new Messenger(
							handler), vorname, nachname);

					Intent geheZuMain = new Intent(SpielerAnlegenActivity.this,
							MainActivity.class);
					startActivity(geheZuMain);
				} else {
					Toast.makeText(SpielerAnlegenActivity.this,
							"Bitte alle Felden ausfüllen", Toast.LENGTH_LONG)
							.show();
				}
			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.spieler_anlegen, menu);
		return true;
	}

}
