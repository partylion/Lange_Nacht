package de.htwg.lange_nacht;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;

public class StrafeEintragenActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_strafe_eintragen);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.strafe_eintragen, menu);
		return true;
	}

}
