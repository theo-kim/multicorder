package com.b2ktechnology.multicorder;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

/**
 * Created by Iggy on 11/18/2016.
 */

public class TabFragmentPagerAdapter extends FragmentPagerAdapter {
    final int PAGE_COUNT = 6;
    private String tabTitles[] = new String[] { "Problem", "Hypothesis", "Materials", "Experiment", "Data Analysis", "Conclusion" };
    private Context context;

    public TabFragmentPagerAdapter(FragmentManager fm, SensorDisplay sensorDisplay) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        return PageFragment.newInstance(position);
    }

    @Override
    public int getCount() {
        return PAGE_COUNT;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        // Generate title based on item position
        return tabTitles[position];
    }
}
