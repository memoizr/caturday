package com.caturday.app.capsules.dashboard.adapter;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.view.ViewGroup;

import com.caturday.app.capsules.dashboard.stream.view.CatStreamFragment;

import java.util.HashMap;

public class DashboardPageAdapter extends FragmentStatePagerAdapter{

    private static int NUM_ITEMS = Categories.values().length;
    private final FragmentManager fragmentManager;
    private HashMap<Integer, Fragment> fragmentHashMap = new HashMap<>();

    public DashboardPageAdapter(FragmentManager fragmentManager) {
        super(fragmentManager);
        this.fragmentManager = fragmentManager;
    }

    /**
     * Return a CatStreamFragment for a given category
     * @param streamCategory
     * @param position
     * @return
     */
    private Fragment fragmentFactory(String streamCategory, int position){
        if (fragmentHashMap.get(position) == null) {
            Fragment fragment = new CatStreamFragment();
            Bundle bundle = new Bundle();
            bundle.putString(CatStreamFragment.STREAM_CATEGORY, streamCategory);
            bundle.putInt(CatStreamFragment.STREAM_POSITION, position);
            bundle.putString(CatStreamFragment.STREAM_USER_ID, null);
            fragment.setArguments(bundle);
            fragmentHashMap.put(position, fragment);
        }
        return fragmentHashMap.get(position);
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

    /**
     * Clean up fragments when container is destroyed
     */
    public void destroyItems() {
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        for (int i : fragmentHashMap.keySet()) {
            transaction.remove(fragmentHashMap.get(i));
        }
        transaction.commit();
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        super.destroyItem(container, position, object);
    }
}
