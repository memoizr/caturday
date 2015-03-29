package com.lovecats.catlover.adapters;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.lovecats.catlover.ui.stream.CatStreamFragment;

/**
 * Created by user on 23/02/15.
 */
public class DashboardPageAdapter extends FragmentStatePagerAdapter{
    public static final int CATEGORY_SPACE = 0;
    public static final int CATEGORY_BOXES = 1;
    public static final int CATEGORY_CATURDAY = 2;
    public static final int CATEGORY_HATS = 3;
    public static final String STREAM_CATEGORY = "streamCategory";
    private static int NUM_ITEMS = 4;

    public DashboardPageAdapter(FragmentManager fragmentManager) {
        super(fragmentManager);
    }

    private Fragment fragmentFactory(int streamCategory){
        Fragment fragment = new CatStreamFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(STREAM_CATEGORY, streamCategory);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public int getCount() {
        return NUM_ITEMS;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return fragmentFactory(CATEGORY_SPACE);
            case 1:
                return fragmentFactory(CATEGORY_BOXES);
            case 2:
                return fragmentFactory(CATEGORY_CATURDAY);
            case 3:
                return fragmentFactory(CATEGORY_HATS);
            default:
                return null;
        }
    }

    @Override
    public CharSequence getPageTitle(int position) {
        switch(position){
            case CATEGORY_SPACE:
                return "Space";
            case CATEGORY_BOXES:
                return "Boxes";
            case CATEGORY_CATURDAY:
                return "Caturday";
            case CATEGORY_HATS:
                return "Hats";
            default:
                return "";
        }
    }
}
