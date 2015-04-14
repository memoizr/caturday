package com.lovecats.catlover.capsules.profile.view;

import android.support.v4.app.FragmentPagerAdapter;

import com.astuetz.PagerSlidingTabStrip;

public interface ProfileView {

    void onPostLogout();

    void showButton(Boolean visible);

    void setUserName(String string);

    void initializePager(FragmentPagerAdapter adapter, PagerSlidingTabStrip slidingTabs_PSTS);

    void initToolbar();
}

