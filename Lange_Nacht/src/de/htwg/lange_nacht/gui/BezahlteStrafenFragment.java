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
import de.htwg.lange_nacht.gui.SpielerUebersicht.BezahlteStrafenFragmentCommunicator;

public class BezahlteStrafenFragment extends Fragment implements
		BezahlteStrafenFragmentCommunicator {

	private ListView lVBezahlteStrafen;
	private View rootView;
	private Context context;
	private ActivityCommunicatorBezahlt activityCommunicator;

	/**
	 * Interface, dass von der Activity SpielerUebersicht implementiert wird um
	 * die Kommunikation mit der Activity zu ermoeglichen 
	 */
	public interface ActivityCommunicatorBezahlt {
		public void callActivityBezahlt();
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		rootView = inflater.inflate(R.layout.fragment_bezahlte_strafen,
				container, false);
		lVBezahlteStrafen = (ListView) rootView
				.findViewById(R.id.listViewBezahlt);
		activityCommunicator.callActivityBezahlt();
		return rootView;
	}

	@Override
	public void passDataToFragment(ArrayList<Strafe> bezahlteStrafen) {
		// Von der Activity übergebene Daten in die ListView einfügen
		ListAdapter adapter = new ArrayAdapter<Strafe>(getActivity()
				.getApplicationContext(), R.layout.simplerow, bezahlteStrafen);

		lVBezahlteStrafen.setAdapter(adapter);
		lVBezahlteStrafen.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
	}

	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);

		// Hier die jeweiligen Interfaces im Fragment und der Activity setzen
		context = getActivity();
		activityCommunicator = (ActivityCommunicatorBezahlt) context;
		((SpielerUebersicht) context).bezahlteStrafenFragmentCommunicator = this;
	}

}
