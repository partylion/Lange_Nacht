package de.htwg.lange_nacht;

import android.app.Activity;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.Spinner;
import android.widget.TextView;

public class StrafeEintragenActivity extends Activity {

	private TextView txtViewPreis;
	private Spinner spinnerSpieler;
	private Spinner spinnerStrafen;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_strafe_eintragen);
		
		txtViewPreis = (TextView) findViewById(R.id.txtViewPreis);
		spinnerSpieler = (Spinner) findViewById(R.id.spinnerSpieler);
		spinnerStrafen = (Spinner) findViewById(R.id.spinnerStrafen);
		
		//Ändert den Inhalt von txtViewPreis entsprechend der ausgewählten Strafe
		spinnerStrafen.setOnItemSelectedListener(new OnItemSelectedListener() {
		    @Override
		    public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
		        //TODO Verhalten beim Auswählen einer Strafe festlegen
		    	txtViewPreis.setTextColor(Color.RED);	
		    	txtViewPreis.setText("25 €");
		    	
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
