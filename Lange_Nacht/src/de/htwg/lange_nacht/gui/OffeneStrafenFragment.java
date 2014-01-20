package de.htwg.lange_nacht.gui;

import java.util.ArrayList;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.Messenger;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;
import de.htwg.lange_nacht.R;
import de.htwg.lange_nacht.business.Strafenverwaltung;
import de.htwg.lange_nacht.data.Messages;
import de.htwg.lange_nacht.data.Strafe;

//TODO Namen irgendwo her holen und bezahltes Element aus der Liste löschen
public class OffeneStrafenFragment extends Fragment {

	private ListView lVOffeneStrafen;
	private Button btnBezahlt;
	private Strafenverwaltung strafenverwaltungsinstanz = Strafenverwaltung
			.getInstance();
	private View rootView;

	@SuppressLint("HandlerLeak")
	private Handler handler = new Handler() {
		public void handleMessage(Message message) {
			if (message.arg1 == android.app.Activity.RESULT_OK
					&& message.arg2 == Messages.UPDATE_STRAFE) {
				Toast.makeText(rootView.getContext(), "Strafe bezahlt",
						Toast.LENGTH_LONG).show();
			} else {
				Toast.makeText(rootView.getContext(), "Upload failed.",
						Toast.LENGTH_LONG).show();
			}
		};
	};

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		rootView = inflater.inflate(R.layout.fragment_offene_strafen,
				container, false);

		lVOffeneStrafen = (ListView) rootView.findViewById(R.id.listViewOffen);

		final ArrayList<Strafe> strafen = strafenverwaltungsinstanz.getStrafenFor(
				"test", "testytest");

		ListAdapter adapter = new ArrayAdapter<Strafe>(getActivity()
				.getApplicationContext(), R.layout.simplerow, strafen);

		lVOffeneStrafen.setAdapter(adapter);
		lVOffeneStrafen.setChoiceMode(ListView.CHOICE_MODE_SINGLE);

		btnBezahlt = (Button) rootView.findViewById(R.id.btnBezahlt);
		btnBezahlt.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				int posi = lVOffeneStrafen.getCheckedItemPosition();
				if (posi != ListView.INVALID_POSITION) {

					strafenverwaltungsinstanz.setBezahlt(new Messenger(handler),
							strafen.get(posi), "vorname", "nachname");
				}
			}
		});

		return rootView;
	}
}