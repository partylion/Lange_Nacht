package de.htwg.lange_nacht.gui;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

public class TabsPagerAdapter extends FragmentPagerAdapter{

	public TabsPagerAdapter(FragmentManager fm) {
		super(fm);
	}

	@Override
	public Fragment getItem(int index) {
		switch (index) {
        case 0:
            // Alle Strafen fragment activity
            return new AlleStrafenFragment();
        case 1:
            // Offene Strafen fragment activity
            return new OffeneStrafenFragment();
        case 2:
            // Bezahlte Strafen fragment activity
            return new BezahlteStrafenFragment();
        }
 
        return null;
	}

	@Override
	public int getCount() {
		// Anzahl der Tabs
		return 3;
	}

}
