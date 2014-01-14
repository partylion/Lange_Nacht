package de.htwg.lange_nacht.gui;

import java.io.IOException;

import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import de.htwg.lange_nacht.R;

public class SpielerAnlegenActivity extends Activity {
	
	private Button btnSpielerAnlegenSubmit;
	private EditText editTextNachname;
	private EditText editTextVorname;

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
				
				String url = "http://localhost/langenacht/insertSpieler.php?";
				url = url + "vorname=" + editTextVorname.getText() + "&"
						+ "nachname=" + editTextNachname.getText();
				
				System.out.println(url);

				HttpClient httpclient = new DefaultHttpClient();
				HttpGet httpget = new HttpGet(url);
				try {
					httpclient.execute(httpget);
				} catch (IOException e) {
					e.printStackTrace();
				}
				
		        Intent geheZuMain = new Intent(SpielerAnlegenActivity.this,
						MainActivity.class);
				startActivity(geheZuMain);
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
