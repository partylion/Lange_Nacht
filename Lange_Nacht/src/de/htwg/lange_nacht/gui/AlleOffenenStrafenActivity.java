package de.htwg.lange_nacht.gui;

import java.util.ArrayList;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.Messenger;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;
import de.htwg.lange_nacht.R;
import de.htwg.lange_nacht.business.Strafenverwaltung;
import de.htwg.lange_nacht.data.Messages;
import de.htwg.lange_nacht.data.Vergehen;

public class AlleOffenenStrafenActivity extends Activity {

	private ListView lvOffeneStrafen;
	private ArrayList<Vergehen> offeneStrafen;
	private Button btnBezahlt;
	private Strafenverwaltung strafenverwaltungsinstanz = Strafenverwaltung
			.getInstance();
	private VergehenAdapter adapter;

	private Handler handler = new Handler() {
		public void handleMessage(Message message) {
			Object list = message.obj;
			if (message.arg1 == RESULT_OK && list != null
					&& message.arg2 == Messages.GET_ALLE_OFFENEN_STRAFEN) {
				offeneStrafen = (ArrayList<Vergehen>) list;
				adapter = new VergehenAdapter(
						AlleOffenenStrafenActivity.this, R.layout.simplerow,
						offeneStrafen);
				lvOffeneStrafen.setAdapter(adapter);
				lvOffeneStrafen.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
				
				lvOffeneStrafen.setOnItemClickListener(new OnItemClickListener() {

					@Override
					public void onItemClick(AdapterView<?> arg0, View arg1,
							int arg2, long arg3) {
						arg1.setSelected(true);						
					}
				});

			} else if (message.arg1 == android.app.Activity.RESULT_OK
					&& message.arg2 == Messages.UPDATE_STRAFE) {
				Toast.makeText(AlleOffenenStrafenActivity.this,
						"Strafe bezahlt", Toast.LENGTH_LONG).show();
				finish();
				startActivity(getIntent());
			} else {
				Toast.makeText(AlleOffenenStrafenActivity.this,
						"Download failed.", Toast.LENGTH_LONG).show();
			}
		};
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_alle_offenen_strafen);

		lvOffeneStrafen = (ListView) findViewById(R.id.listViewOffeneStrafenActivity);
		btnBezahlt = (Button) findViewById(R.id.btnBezahltActivity);

		btnBezahlt.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				int posi = lvOffeneStrafen.getCheckedItemPosition();
				if (posi != ListView.INVALID_POSITION) {

					Vergehen bezahlt = offeneStrafen.get(posi);

					strafenverwaltungsinstanz.setBezahlt(
							new Messenger(handler), bezahlt);
				} else {
					Toast.makeText(AlleOffenenStrafenActivity.this,
							"Bitte zuerst ein Element auswählen.",
							Toast.LENGTH_LONG).show();
				}

			}
		});

		strafenverwaltungsinstanz.getAllOffeneStrafen(this, new Messenger(
				handler));

	}
}
