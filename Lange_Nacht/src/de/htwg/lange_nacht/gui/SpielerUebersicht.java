package de.htwg.lange_nacht.gui;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.widget.TextView;
import de.htwg.lange_nacht.R;

public class SpielerUebersicht extends ActionBarActivity implements
		android.support.v7.app.ActionBar.TabListener{

	private TextView txtViewSpielerName;
//	private Strafenverwaltung strafenverwaltungsinstanz = Strafenverwaltung
//			.getInstance();

	private ViewPager viewPager;
	private TabsPagerAdapter mAdapter;
	private ActionBar actionBar;
	// Tab titles
	private String[] tabs = { "Alle", "Offen", "Bezahlt" };

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

		String spielerName = getIntent().getExtras().getString("Spielername");

		txtViewSpielerName = (TextView) findViewById(R.id.txtViewSpielerName);
		txtViewSpielerName.setText(spielerName);
//
//		String[] name = spielerName.split("\\s");
//		String vorname = name[0];
//		String nachname = name[1];
//
//		// TODO Information zum Spieler holen und übersichtlich darstellen
//		ArrayList<Strafe> strafen = strafenverwaltungsinstanz.getStrafenFor(vorname,
//				nachname);
//		
//		ArrayList<String> strafenString = new ArrayList<String>();
//		
//		for (int i = 0; i < strafen.size(); i++) {
//			strafenString.add(strafen.get(i).getBeschreibung() + " " + strafen.get(i).getPreis());			
//		}
//
//		lVOffeneStrafen = (ListView) findViewById(R.id.listViewOffen);
//
//		ListAdapter adapter = new ArrayAdapter<Strafe>(getApplicationContext(),
//				android.R.layout.simple_list_item_1, strafen);
//		
//		OffeneStrafenFragment offeneStrafenFragmentInstanz = OffeneStrafenFragment.getInstance();
//
//		offeneStrafenFragmentInstanz.fillList(adapter);

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

}
