package com.lovecats.catlover.capsules.profile.view;

import android.support.v4.app.FragmentPagerAdapter;
import android.support.v7.widget.Toolbar;

import com.astuetz.PagerSlidingTabStrip;

public interface ProfileView {

    void showButton(Boolean visible);

    void initializePager(FragmentPagerAdapter adapter, PagerSlidingTabStrip slidingTabs_PSTS);

    void setProfileImage(String imageUrl);

    void setUsername(String string);

    void setCoverImage(String coverImageUrl);

    void setupCollapsibleToolbar(Toolbar toolbar);

    Toolbar getToolbar();
}

