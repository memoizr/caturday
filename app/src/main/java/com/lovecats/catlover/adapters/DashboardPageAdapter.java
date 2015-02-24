package com.lovecats.catlover.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.lovecats.catlover.FavoriteCatsFragment;
import com.lovecats.catlover.NewCatsFragment;

/**
 * Created by user on 23/02/15.
 */
public class DashboardPageAdapter extends FragmentStatePagerAdapter{
    private static int NUM_ITEMS = 2;

    public DashboardPageAdapter(FragmentManager fragmentManager) {
        super(fragmentManager);
    }

    @Override
    public int getCount() {
        return NUM_ITEMS;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return new NewCatsFragment();
            case 1:
                return new FavoriteCatsFragment();
            default:
                return null;
        }
    }

    @Override
    public CharSequence getPageTitle(int position) {
        switch(position){
            case 0:
                return "New";
            case 1:
                return "Favourite";
            default:
                return "";
        }
    }

}
