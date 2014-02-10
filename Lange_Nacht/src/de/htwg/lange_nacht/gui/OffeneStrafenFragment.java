package de.htwg.lange_nacht.gui;

import java.util.ArrayList;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.Messenger;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;
import de.htwg.lange_nacht.R;
import de.htwg.lange_nacht.business.Strafenverwaltung;
import de.htwg.lange_nacht.data.Messages;
import de.htwg.lange_nacht.data.Vergehen;
import de.htwg.lange_nacht.gui.SpielerUebersicht.OffeneStrafenFragmentCommunicator;

public class OffeneStrafenFragment extends Fragment implements
		OffeneStrafenFragmentCommunicator {

	private ListView lVOffeneStrafen;
	private Button btnBezahlt;
	private Strafenverwaltung strafenverwaltungsinstanz = Strafenverwaltung
			.getInstance();
	private ArrayList<Vergehen> offeneStrafen;
	private View rootView;
	private Context context;
	private ActivityCommunicatorOffen activityCommunicator;

	/**
	 * Interface, dass von der Activity SpielerUebersicht implementiert wird um
	 * die Kommunikation mit der Activity zu ermoeglichen
	 */
	public interface ActivityCommunicatorOffen {
		/**
		 * Ruft die Activity
		 */
		public void callActivityOffen();
		
		/**
		 * 
		 */
		public void refreshActivity();
	}

	// Behandelt die Antwort des AsyncTasks, der eine Strafe von offen auf
	// bezahlt setzen kann
	@SuppressLint("HandlerLeak")
	private Handler handler = new Handler() {
		public void handleMessage(Message message) {
			if (message.arg1 == android.app.Activity.RESULT_OK
					&& message.arg2 == Messages.UPDATE_STRAFE) {
				Toast.makeText(rootView.getContext(), "Strafe bezahlt",
						Toast.LENGTH_LONG).show();
				activityCommunicator.refreshActivity();
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

		btnBezahlt = (Button) rootView.findViewById(R.id.btnBezahlt);
		btnBezahlt.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				int posi = lVOffeneStrafen.getCheckedItemPosition();
				if (posi != ListView.INVALID_POSITION) {

					Vergehen bezahlt = offeneStrafen.get(posi);

					strafenverwaltungsinstanz.setBezahlt(
							new Messenger(handler), bezahlt);
					
				} else {
					Toast.makeText(rootView.getContext(),
							"Bitte zuerst ein Element auswählen.",
							Toast.LENGTH_LONG).show();
				}
			}

		});

		activityCommunicator.callActivityOffen();

		return rootView;
	}

	@Override
	public void passDataToFragment(ArrayList<Vergehen> offeneStrafen) {
		// Von der Activity übergebene Daten in die ListView einfügen
		this.offeneStrafen = offeneStrafen;
		VergehenAdapter adapter = new VergehenAdapter(
				getActivity().getApplicationContext(), R.layout.simplerow,
				offeneStrafen);
		lVOffeneStrafen.setAdapter(adapter);
		lVOffeneStrafen.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
		
		lVOffeneStrafen.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1,
					int arg2, long arg3) {
				arg1.setSelected(true);						
			}
		});
	}

	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);

		// Hier die jeweiligen Interfaces im Fragment und der Activity setzen
		context = getActivity();
		activityCommunicator = (ActivityCommunicatorOffen) context;
		((SpielerUebersicht) context).offeneStrafenFragmentCommunicator = this;
	}

}