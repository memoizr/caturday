package com.caturday.app.capsules.main.view;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.Toolbar;

import com.caturday.app.capsules.common.listener.ScrollEventListener;
import com.daimajia.slider.library.SliderLayout;

public interface MainView extends ScrollEventListener {
    Toolbar getToolbar();

    SliderLayout getSliderLayout();

    void setupCollapsibleToolbar(Toolbar toolbar);

    void setDrawer(Activity activity, Toolbar toolbar, DrawerLayout drawerLayout);

    void toggleArrow(boolean toggle);

    void setUpFragments(Bundle savedInstanceState);

    void onRefreshCompleted();

    int getCollapsedThreshold();

    void hideToolBarContainer(boolean shouldHide);
}
