package com.lovecats.catlover.capsules.dashboard.adapter;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.lovecats.catlover.capsules.dashboard.stream.view.CatStreamFragment;

public class DashboardPageAdapter extends FragmentStatePagerAdapter{

    public static final String STREAM_CATEGORY = "streamCategory";
    public static final String STREAM_POSITION = "streamPosition";
    private static int NUM_ITEMS = Categories.values().length;

    public DashboardPageAdapter(FragmentManager fragmentManager) {
        super(fragmentManager);
    }

    private Fragment fragmentFactory(String streamCategory, int position){
        Fragment fragment = new CatStreamFragment();
        Bundle bundle = new Bundle();
        bundle.putString(STREAM_CATEGORY, streamCategory);
        bundle.putInt(STREAM_POSITION, position);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public int getCount() {
        return NUM_ITEMS;
    }

    @Override
    public Fragment getItem(int position) {
        return fragmentFactory(Categories.values()[position].toString(), position);
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return Categories.getCategory(position);
    }
}
