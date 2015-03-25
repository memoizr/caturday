package com.lovecats.catlover.ui.activities;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AbsListView;
import android.widget.RelativeLayout;

import com.astuetz.PagerSlidingTabStrip;
import com.github.ksoichiro.android.observablescrollview.ScrollState;
import com.lovecats.catlover.data.UserModel;
import com.lovecats.catlover.ui.fragments.CatStreamFragment;
import com.lovecats.catlover.Config;
import com.lovecats.catlover.ui.fragments.DashboardFragment;
import com.lovecats.catlover.R;
import com.lovecats.catlover.data.DaoManager;
import com.lovecats.catlover.helpers.AnimationHelper;
import com.lovecats.catlover.helpers.DrawerArrowHelper;
import com.lovecats.catlover.ui.fragments.NavigationFragment;
import com.lovecats.catlover.util.HyperAccelerateDecelerateInterpolator;
import com.lovecats.catlover.ui.views.CollapsibleView;
import com.lovecats.catlover.ui.views.SlideShowView;

import java.util.Arrays;
import java.util.List;

import javax.inject.Inject;

import butterknife.ButterKnife;
import butterknife.InjectView;
import lombok.Getter;

public class MainActivity extends BaseActionBarActivity
        implements NavigationFragment.OnFragmentInteractionListener,
        CatStreamFragment.ScrollCallback {

    @Inject CollapsibleView collapsibleView;
    @Inject DashboardFragment dashboardFragment;
    @Inject NavigationFragment navigationFragment;

    public PagerSlidingTabStrip slidingTabs;
    @InjectView(R.id.toolbar) Toolbar mToolbar;
    @InjectView(R.id.title_container_RL) RelativeLayout title_container_RL;
    @InjectView(R.id.slide_show_V) SlideShowView slide_show_V;
    @InjectView(R.id.sliding_PSTS) PagerSlidingTabStrip slidingTabs_PSTS;
    @InjectView(R.id.main_container_V) DrawerLayout mDrawerLayout;
    @InjectView(R.id.status_bar_scrim) View status_bar_scrim;
    private int titleMaxHeight;
    private int titleMinHeight;
    private int titleCollapsed;
    @Getter private int oldScrollY;
    private boolean transparent = true;
    private boolean statusTransparent = true;
    private ActionBarDrawerToggle mDrawerToggle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.inject(this);

        slidingTabs = slidingTabs_PSTS;

        getWindow().getDecorView().setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);

        setUpFragments(savedInstanceState);

        setupCollapsibleToolbar(mToolbar);
        setupMenuClickListener(mToolbar);
        setDrawer(this, mToolbar);

        titleCollapsed = getResources().getDimensionPixelSize(R.dimen.title_collapsed);
    }

    @Override
    protected List<Object> getModules() {
        return Arrays.<Object>asList(new MainModule(this));
    }

    private void setUpFragments(Bundle savedInstanceState) {
        if (savedInstanceState == null) {

            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container, dashboardFragment)
                    .commit();
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.left_drawer_V, navigationFragment)
                    .commit();
        }
    }

    private void setupCollapsibleToolbar(Toolbar toolbar) {

        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("");

        toolbar.addView(collapsibleView);

        titleMaxHeight = collapsibleView.getMaxHeight();
        titleMinHeight = collapsibleView.getMinHeight();
    }

    private void setupMenuClickListener(Toolbar toolbar) {
        toolbar.setOnMenuItemClickListener(
                new Toolbar.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        toggleArrow(true);
//                        slide_show_V.animationPause();
                        if (item.getItemId() == R.id.action_login) {
                            Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                            startActivity(intent);
                        } else {
                            Intent intent = new Intent(getApplicationContext(), SettingsActivity.class);
                            startActivity(intent);
                        }
                        return true;
                    }
                });
    }

    private void setDrawer(Activity activity, Toolbar toolbar) {
        mDrawerToggle = new ActionBarDrawerToggle(activity, mDrawerLayout, toolbar, R.string.action_settings, R.string.action_login);
        mDrawerLayout.post(new Runnable() {
            @Override
            public void run() {
                mDrawerToggle.syncState();
            }
        });
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        mDrawerLayout.setDrawerListener(mDrawerToggle);
    }

    public void toggleArrow(boolean toggle) {
        DrawerArrowHelper.toggleArrow(toggle, mDrawerToggle, mDrawerLayout);
    }

    @Override
    public void onFragmentInteraction(int position) {
        switch (position) {
            case 0:
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.container, dashboardFragment)
                        .commit();
                break;
            default:
                break;
        }
    }

    public void doneSlideshow() {
        slide_show_V.flash();
    }

    @Override
    public void onScroll(Fragment fragment, int scrollY,
                         boolean firstScroll, boolean dragging) {

        slide_show_V.setTranslationY(-(float) (scrollY * 0.8));

        title_container_RL.animate().cancel();

        // Note: positive delta means scrolling up
        int scrollDelta = scrollY - oldScrollY;
        oldScrollY = scrollY;

        if (scrollY == 0) {
            dashboardFragment.enableSwipeToRefresh(true);
        } else {
            dashboardFragment.enableSwipeToRefresh(false);
        }

        if (scrollY < titleMaxHeight - titleMinHeight) {
            collapsibleView.setCollapseLevel(
                    Math.max(1f - scrollY / (float) (titleMaxHeight - titleMinHeight), 0));
            if (!transparent) {
                AnimationHelper.animateColor(title_container_RL,
                        getResources().getColor(R.color.primary),
                        getResources().getColor(R.color.primary_transparent));
            }
            if (!statusTransparent) {

                AnimationHelper.animateColor(status_bar_scrim,
                        getResources().getColor(R.color.primary),
                        getResources().getColor(R.color.primary_transparent));
                transparent = statusTransparent = true;

                slide_show_V.animationStart();
            }
            title_container_RL.setTranslationY(0);

        } else if (scrollY < titleMaxHeight && scrollDelta > 0) {

            collapsibleView.setCollapseLevel(0);
            if (statusTransparent) {
                AnimationHelper.animateColor(status_bar_scrim,
                        getResources().getColor(R.color.primary_transparent),
                        getResources().getColor(R.color.primary));
                statusTransparent = false;
            }

            float shiftY = (scrollY - titleCollapsed);
            shiftY = Math.min(Math.max(shiftY, 0), titleCollapsed);
            title_container_RL.setTranslationY(-shiftY + 16);

        } else {

            collapsibleView.setCollapseLevel(0);

            if (transparent && scrollY > titleMaxHeight + titleMinHeight) {
                AnimationHelper.animateColor(title_container_RL,
                        getResources().getColor(R.color.primary_transparent),
                        getResources().getColor(R.color.primary));

                slide_show_V.animationPause();
                transparent = false;
            }

            // To avoid confusion about sign, use shiftY instead of transitionY.
            // shiftY has same sign as scrollY and always positive or 0.
            float shiftY = -title_container_RL.getTranslationY();
            shiftY = Math.min(Math.max(shiftY + scrollDelta, 0), titleCollapsed);
            title_container_RL.setTranslationY(-shiftY);
        }
    }

    @Override
    public void onUpOrCancelMotionEvent(Fragment fragment, ScrollState scrollState) {
        if (scrollState == ScrollState.STOP) {
            adjustToolbarOnEndOfScroll();
        }
    }

    @Override
    public void onScrollStateChanged(Fragment fragment, int scrollState) {
        if (scrollState == AbsListView.OnScrollListener.SCROLL_STATE_IDLE) {
            adjustToolbarOnEndOfScroll();
        }
    }

    private void adjustToolbarOnEndOfScroll() {
        float shiftY = -title_container_RL.getTranslationY();

        float targetShiftY = 0;
        if (shiftY > titleMinHeight &&
                oldScrollY >= titleMaxHeight) {

            targetShiftY = titleCollapsed;
        }

        if (transparent && shiftY > 0) {
            AnimationHelper.animateColor(title_container_RL,
                    getResources().getColor(R.color.primary_transparent),
                    getResources().getColor(R.color.primary));
            slide_show_V.animationPause();
            transparent = false;
        }

        if (title_container_RL.getTranslationY() != -targetShiftY) {
            title_container_RL.animate().cancel();
            title_container_RL
                    .animate()
                    .translationY(-targetShiftY)
                    .setDuration(300)
                    .setInterpolator(new HyperAccelerateDecelerateInterpolator())
                    .start();
        }
    }


    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        MenuItem item = menu.findItem(R.id.action_login);
        if (item != null){
            item.setVisible(!UserModel.userLoggedIn());
        }
        return true;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_dashboard, menu);
        return true;
    }

    @Override
    public void onResume() {
        super.onResume();
        slide_show_V.animationResume();
        toggleArrow(false);
    }
}
