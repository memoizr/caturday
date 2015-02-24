package com.lovecats.catlover;

import android.annotation.TargetApi;
import android.app.Activity;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.transition.Fade;
import android.transition.Transition;

import com.astuetz.PagerSlidingTabStrip;
import com.lovecats.catlover.adapters.DashboardPageAdapter;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by user on 23/02/15.
 */
public class DashboardActivity extends ActionBarActivity {
    @InjectView(R.id.toolbar) Toolbar toolbar;
    @InjectView(R.id.dashboard_VP) ViewPager dashboard_VP;
    @InjectView(R.id.sliding_PSTS) PagerSlidingTabStrip slidingTabs_PSTS;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        ButterKnife.inject(this);

        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Caturday");

        dashboard_VP.setAdapter(new DashboardPageAdapter(getSupportFragmentManager()));
        slidingTabs_PSTS.setViewPager(dashboard_VP);
        slidingTabs_PSTS.setTextColor(getResources().getColor(R.color.white));
        Transition fade = new Fade();
        fade.excludeTarget(android.R.id.statusBarBackground, true);
        fade.excludeTarget(android.R.id.navigationBarBackground, true);
        getWindow().setExitTransition(fade);
        getWindow().setEnterTransition(fade);

    }
}
