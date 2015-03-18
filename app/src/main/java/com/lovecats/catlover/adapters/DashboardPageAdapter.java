package com.lovecats.catlover.adapters;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.lovecats.catlover.CatStreamFragment;

/**
 * Created by user on 23/02/15.
 */
public class DashboardPageAdapter extends FragmentStatePagerAdapter{
    private static int NUM_ITEMS = 4;

//    public DashBoardPageAdapter() {
//        Fragment fragmentNew = new CatStreamFragment();
//        Bundle bundleNew = new Bundle();
//        bundleNew.putInt("streamType", 0);
//        fragmentNew.setArguments(bundleNew);
//        Fragment fragmentFavorites = new CatStreamFragment();
//        Bundle bundleFavorites = new Bundle();
//        bundleFavorites.putInt("streamType", 1);
//        fragmentFavorites.setArguments(bundleFavorites);
//    }
    Fragment fragmentNew;
    Fragment fragmentFavorites;
    public DashboardPageAdapter(FragmentManager fragmentManager) {
        super(fragmentManager);

//        fragmentNew = new CatStreamFragment();
//        Bundle bundleNew = new Bundle();
//        bundleNew.putInt("streamType", 0);
//        fragmentNew.setArguments(bundleNew);
//        fragmentFavorites = new CatStreamFragment();
//        Bundle bundleFavorites = new Bundle();
//        bundleFavorites.putInt("streamType", 1);
//        fragmentFavorites.setArguments(bundleFavorites);
    }

    private Fragment fragmentFactory(int streamType){
        Fragment fragment = new CatStreamFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("streamType", streamType);
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
                return fragmentFactory(0);
            case 1:
                return fragmentFactory(1);
            case 2:
                return fragmentFactory(2);
            case 3:
                return fragmentFactory(3);
            default:
                return null;
        }
    }

    @Override
    public CharSequence getPageTitle(int position) {
        switch(position){
            case 0:
                return "Space";
            case 1:
                return "Boxes";
            case 2:
                return "Caturday";
            case 3:
                return "Hats";
            default:
                return "";
        }
    }

}
