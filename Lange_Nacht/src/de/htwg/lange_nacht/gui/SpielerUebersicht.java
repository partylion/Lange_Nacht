package de.htwg.lange_nacht.gui;

import java.util.ArrayList;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.Messenger;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.widget.TextView;
import android.widget.Toast;
import de.htwg.lange_nacht.R;
import de.htwg.lange_nacht.business.Strafenverwaltung;
import de.htwg.lange_nacht.data.Messages;
import de.htwg.lange_nacht.data.Spieler;
import de.htwg.lange_nacht.data.Strafe;
import de.htwg.lange_nacht.gui.AlleStrafenFragment.ActivityCommunicatorAlle;
import de.htwg.lange_nacht.gui.BezahlteStrafenFragment.ActivityCommunicatorBezahlt;
import de.htwg.lange_nacht.gui.OffeneStrafenFragment.ActivityCommunicatorOffen;

public class SpielerUebersicht extends ActionBarActivity implements
		android.support.v7.app.ActionBar.TabListener,
		ActivityCommunicatorOffen, ActivityCommunicatorBezahlt,
		ActivityCommunicatorAlle {

	private TextView txtViewSpielerName;
	private Strafenverwaltung strafenverwaltungsinstanz = Strafenverwaltung
			.getInstance();

	private ViewPager viewPager;
	private TabsPagerAdapter mAdapter;
	private ActionBar actionBar;
	private String vorname, nachname;
	// Tab titles
	private String[] tabs = { "Alle", "Offen", "Bezahlt" };
	public OffeneStrafenFragmentCommunicator offeneStrafenFragmentCommunicator;
	public BezahlteStrafenFragmentCommunicator bezahlteStrafenFragmentCommunicator;
	public AlleStrafenFragmentCommunicator alleStrafenFragmentCommunicator;

	private Handler handler = new Handler() {
		public void handleMessage(Message message) {
			Object list = message.obj;
			System.out.println("kommt hier was?");
			if (message.arg1 == RESULT_OK && list != null
					&& message.arg2 == Messages.GET_ALLE_STRAFEN_FOR) {
				ArrayList<Strafe> alleStrafen = (ArrayList<Strafe>) list;
				alleStrafenFragmentCommunicator.passDataToFragment(alleStrafen);
			} else if (message.arg1 == RESULT_OK && list != null
					&& message.arg2 == Messages.GET_OFFENE_STRAFEN_FOR) {
				ArrayList<Strafe> offeneStrafen = (ArrayList<Strafe>) list;
				offeneStrafenFragmentCommunicator.passDataToFragment(offeneStrafen);
			}
			else if (message.arg1 == RESULT_OK && list != null
					&& message.arg2 == Messages.GET_BEZAHLTE_STRAFEN_FOR) {
				ArrayList<Strafe> bezahlteStrafen = (ArrayList<Strafe>) list;
				bezahlteStrafenFragmentCommunicator.passDataToFragment(bezahlteStrafen);
			} else {
				Toast.makeText(SpielerUebersicht.this,
						"Download failed.", Toast.LENGTH_LONG).show();
			}
		};
	};

	/**
	 * Interface, dass vom Fragment OffeneStrafenFragment implementiert wird um
	 * die Kommunikation mit dem Fragment zu ermoeglichen
	 */
	public interface OffeneStrafenFragmentCommunicator {
		/**
		 * Uebergibt eine ArrayList mit allen offenen Strafen des Spielers an
		 * das Fragment
		 * 
		 * @param offeneStrafen
		 *            ArrayList mit allen offenen Strafen des Spielers
		 */
		public void passDataToFragment(ArrayList<Strafe> offeneStrafen);
	}

	/**
	 * Interface, dass vom Fragment BezahlteStrafenFragment implementiert wird
	 * um die Kommunikation mit dem Fragment zu ermoeglichen
	 */
	public interface BezahlteStrafenFragmentCommunicator {
		/**
		 * Uebergibt eine ArrayList mit allen bezahlten Strafen des Spielers an
		 * das Fragment
		 * 
		 * @param bezahlteStrafen
		 *            ArrayList mit allen bezahlten Strafen des Spielers
		 */
		public void passDataToFragment(ArrayList<Strafe> bezahlteStrafen);
	}

	/**
	 * Interface, dass vom Fragment AlleStrafenFragment implementiert wird um
	 * die Kommunikation mit dem Fragment zu ermoeglichen
	 */
	public interface AlleStrafenFragmentCommunicator {
		/**
		 * Uebergibt eine ArrayList mit allen Strafen des Spielers an das
		 * Fragment
		 * 
		 * @param alleStrafen
		 *            ArrayList mit allen bezahlten Strafen des Spielers
		 */
		public void passDataToFragment(ArrayList<Strafe> alleStrafen);
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_spieler_uebersicht);

		// Initilization
		viewPager = (ViewPager) findViewById(R.id.pager);
		actionBar = getSupportActionBar();
		mAdapter = new TabsPagerAdapter(getSupportFragmentManager());

		viewPager.setAdapter(mAdapter);
		actionBar.setHomeButtonEnabled(false);
		actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);

		// Adding Tabs
		for (String tab_name : tabs) {
			actionBar.addTab(actionBar.newTab().setText(tab_name)
					.setTabListener(this));
		}

		/**
		 * on swiping the viewpager make respective tab selected
		 * */
		viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {

			@Override
			public void onPageSelected(int position) {
				// on changing the page
				// make respected tab selected
				actionBar.setSelectedNavigationItem(position);
			}

			@Override
			public void onPageScrolled(int arg0, float arg1, int arg2) {
			}

			@Override
			public void onPageScrollStateChanged(int arg0) {
			}
		});

		// Uebergabeparameter der vorhergehenden Activity holen
		String spielerName = getIntent().getExtras().getString("Spielername");

		txtViewSpielerName = (TextView) findViewById(R.id.txtViewSpielerName);
		txtViewSpielerName.setText(spielerName);

		// und in Vorname und Nachname aufsplitten
		String[] name = spielerName.split("\\s");
		vorname = name[0];
		nachname = name[1];
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.spieler_uebersicht, menu);
		return true;
	}

	@Override
	public void onTabReselected(android.support.v7.app.ActionBar.Tab tab,
			android.support.v4.app.FragmentTransaction ft) {

	}

	@Override
	public void onTabSelected(android.support.v7.app.ActionBar.Tab tab,
			android.support.v4.app.FragmentTransaction ft) {
		// on tab selected
		// show respected fragment view
		viewPager.setCurrentItem(tab.getPosition());
	}

	@Override
	public void onTabUnselected(android.support.v7.app.ActionBar.Tab tab,
			android.support.v4.app.FragmentTransaction ft) {

	}

	@Override
	public void callActivityOffen() {
		strafenverwaltungsinstanz.getOffeneStrafenFor(this, new Messenger(
				handler), vorname, nachname);
	}

	@Override
	public void callActivityBezahlt() {
		strafenverwaltungsinstanz.getBezahlteStrafenFor(this, new Messenger(
				handler), vorname, nachname);
	}

	@Override
	public void callActivityAlle() {
		strafenverwaltungsinstanz.getAllStrafenFor(this,
				new Messenger(handler), vorname, nachname);
	}

}
