package de.htwg.lange_nacht.gui;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import de.htwg.lange_nacht.R;
import de.htwg.lange_nacht.data.Strafe;
import de.htwg.lange_nacht.gui.SpielerUebersicht.AlleStrafenFragmentCommunicator;

public class AlleStrafenFragment extends Fragment implements
		AlleStrafenFragmentCommunicator {

	private ListView lVAlleStrafen;
	private View rootView;
	private Context context;
	private ActivityCommunicatorAlle activityCommunicator;

	/**
	 * Interface, dass von der Activity SpielerUebersicht implementiert wird um
	 * die Kommunikation mit der Activity zu ermoeglichen 
	 */
	public interface ActivityCommunicatorAlle {
		public void callActivityAlle();
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		rootView = inflater.inflate(R.layout.fragment_alle_strafen,
				container, false);
		lVAlleStrafen = (ListView) rootView.findViewById(R.id.listViewAlle);
		activityCommunicator.callActivityAlle();
		return rootView;
	}

	@Override
	public void passDataToFragment(ArrayList<Strafe> alleStrafen) {
		// Von der Activity übergebene Daten in die ListView einfügen
		ListAdapter adapter = new ArrayAdapter<Strafe>(getActivity()
				.getApplicationContext(), R.layout.simplerow, alleStrafen);

		lVAlleStrafen.setAdapter(adapter);
		lVAlleStrafen.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
	}

	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);

		// Hier die jeweiligen Interfaces im Fragment und der Activity setzen
		context = getActivity();
		activityCommunicator = (ActivityCommunicatorAlle) context;
		((SpielerUebersicht) context).alleStrafenFragmentCommunicator = this;
	}

}
