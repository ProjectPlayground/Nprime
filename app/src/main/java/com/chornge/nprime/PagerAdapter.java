package com.chornge.nprime;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;

public class PagerAdapter extends FragmentPagerAdapter {

    int tabCount;

    //mandatory constructor
    public PagerAdapter(android.support.v4.app.FragmentManager fm, int numberOfTabs) {
        super(fm);
        this.tabCount = numberOfTabs;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                //tab 1
                return new UserProfileFragment();
            case 1:
                //tab 2
                return new MessagesFragment();
            case 2:
                //tab 3
                return new ExploreFragment();
            case 3:
                //tab 4
                return new EventsFragment();
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return tabCount;
    }
}