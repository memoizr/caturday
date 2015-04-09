package com.lovecats.catlover.capsules.main.view;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.Toolbar;

import com.daimajia.slider.library.SliderLayout;

public interface MainView {
    Toolbar getToolbar();

    SliderLayout getSliderLayout();

    void setupCollapsibleToolbar(Toolbar toolbar);

    void setDrawer(Activity activity, Toolbar toolbar, DrawerLayout drawerLayout);

    void toggleArrow(boolean toggle);

    void setUpFragments(Bundle savedInstanceState);

    void onRefreshCompleted();
}
