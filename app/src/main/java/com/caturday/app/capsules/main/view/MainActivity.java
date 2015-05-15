package com.caturday.app.capsules.main.view;

import android.animation.Animator;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.widget.AbsListView;
import android.widget.RelativeLayout;

import com.astuetz.PagerSlidingTabStrip;
import com.caturday.app.R;
import com.caturday.app.capsules.common.view.views.CollapsibleView;
import com.caturday.app.capsules.dashboard.DashboardFragment;
import com.caturday.app.capsules.dashboard.SlidingTabActivity;
import com.caturday.app.capsules.dashboard.stream.view.CatStreamFragment;
import com.caturday.app.capsules.drawer.view.DrawerActivity;
import com.caturday.app.capsules.drawer.view.NavigationFragment;
import com.caturday.app.capsules.main.MainModule;
import com.caturday.app.capsules.main.presenter.MainPresenter;
import com.caturday.app.util.helper.AnimationHelper;
import com.caturday.app.util.helper.DrawerArrowHelper;
import com.caturday.app.util.interpolators.HyperAccelerateDecelerateInterpolator;
import com.caturday.app.util.interpolators.HyperTanAccelerateInterpolator;
import com.caturday.app.util.interpolators.HyperTanDecelerateInterpolator;
import com.daimajia.slider.library.SliderLayout;
import com.github.ksoichiro.android.observablescrollview.ScrollState;

import java.util.Arrays;
import java.util.List;

import javax.inject.Inject;

import butterknife.ButterKnife;
import butterknife.InjectView;
import lombok.Getter;

public class MainActivity extends DrawerActivity implements
        SlidingTabActivity, MainView {

    public PagerSlidingTabStrip slidingTabs;
    @Inject CollapsibleView collapsibleView;
    @Inject DashboardFragment dashboardFragment;
    @Inject MainPresenter mainPresenter;
    @Inject NavigationFragment navigationFragment;
    @Getter @InjectView(R.id.slider) SliderLayout sliderLayout;
    @Getter @InjectView(R.id.toolbar) Toolbar toolbar;
    @InjectView(R.id.main_container_V) DrawerLayout mDrawerLayout;
    @InjectView(R.id.sliding_PSTS) PagerSlidingTabStrip slidingTabs_PSTS;
    @InjectView(R.id.status_bar_scrim) View status_bar_scrim;
    @InjectView(R.id.title_container_RL) RelativeLayout title_container_RL;
    @InjectView(R.id.reveal_V) View reveal;
    @Getter private int oldScrollY;
    private ActionBarDrawerToggle mDrawerToggle;
    private boolean statusTransparent = true;
    private boolean transparent = true;
    private int titleCollapsed;
    private int titleMaxHeight;
    private int titleMinHeight;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.inject(this);

        slidingTabs = slidingTabs_PSTS;

        getWindow().getDecorView().setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);

        mainPresenter.create(savedInstanceState);
        setDrawer(this, toolbar, mDrawerLayout);

        titleCollapsed = getResources().getDimensionPixelSize(R.dimen.title_collapsed);
    }

    @Override
    protected List<Object> getModules() {
        return Arrays.asList(new MainModule(this));
    }

    @Override
    public void setupCollapsibleToolbar(Toolbar toolbar) {

        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("");

        toolbar.addView(collapsibleView);

        titleMaxHeight = collapsibleView.getMaxHeight();
        titleMinHeight = collapsibleView.getMinHeight();
    }

    @Override
    public void setDrawer(Activity activity, Toolbar toolbar, DrawerLayout drawerLayout) {
        mDrawerToggle = new ActionBarDrawerToggle(activity, drawerLayout, toolbar, R.string.drawer_open_desc, R.string.drawer_close_desc);
        mDrawerLayout.post(() -> mDrawerToggle.syncState());
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        mDrawerLayout.setDrawerListener(mDrawerToggle);
    }

    @Override
    public void toggleArrow(boolean toggle) {
        DrawerArrowHelper.toggleArrow(toggle, mDrawerToggle, mDrawerLayout);
    }

    @Override
    public void setUpFragments(Bundle savedInstanceState) {
        if (savedInstanceState == null) {

            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container, dashboardFragment)
                    .commit();

            getSupportFragmentManager().beginTransaction()
                    .add(R.id.left_drawer_V, navigationFragment)
                    .commit();
        }
    }

    @Override
    public void onRefreshCompleted() {
        reveal().addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) { }

            @Override
            public void onAnimationEnd(Animator animation) {
                fade();
            }

            @Override
            public void onAnimationCancel(Animator animation) {  }

            @Override
            public void onAnimationRepeat(Animator animation) {  }
        });
    }

    @Override
    public int getCollapsedThreshold() {
        return titleCollapsed + getResources().getDimensionPixelSize(R.dimen.size_xsmall);
    }

    @Override
    public void hideToolBarContainer(boolean shouldHide) {
        int targetShiftY = shouldHide ? titleCollapsed : 0;

        if (transparent && oldScrollY > titleMinHeight) {
            AnimationHelper.animateColor(title_container_RL,
                    getResources().getColor(R.color.primary_transparent),
                    getResources().getColor(R.color.primary));
            pauseSliderAnimation();
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
    public void showTabs(boolean showTabs) {
        if (slidingTabs_PSTS != null) {
            if (showTabs)
                slidingTabs_PSTS.setVisibility(View.VISIBLE);
            else
                slidingTabs_PSTS.setVisibility(View.INVISIBLE);
        }
    }

    private Animator reveal() {
        int cx = reveal.getWidth() / 2;
        int cy = reveal.getTop() + 96;

        int finalRadius = (int) Math.sqrt(Math.pow(reveal.getWidth(), 2) + Math.pow(reveal.getHeight(), 2));

        Animator anim =
                ViewAnimationUtils.createCircularReveal(reveal, cx, cy, 0, finalRadius);

        anim.setDuration(400);
        anim.setInterpolator(new HyperTanAccelerateInterpolator());

        // make the view visible and start the animation
        reveal.setVisibility(View.VISIBLE);
        reveal.setAlpha(1f);
        anim.start();
        return anim;
    }

    private void fade() {
        reveal.animate()
                .alpha(0f).setDuration(600)
                .setInterpolator(new HyperTanDecelerateInterpolator())
                .start();
    }

    @Override
    public void onScrollChanged(int scrollY, boolean dragging) {

        sliderLayout.setTranslationY(-(float) (scrollY * 0.8));

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

                resumeSliderAnimation();
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

                pauseSliderAnimation();
                transparent = false;
            }

            // To avoid confusion about sign, use shiftY instead of transitionY.
            // shiftY has same sign as scrollY and always positive or 0.
            float shiftY = -title_container_RL.getTranslationY();
            shiftY = Math.min(Math.max(shiftY + scrollDelta, 0), titleCollapsed);
            title_container_RL.setTranslationY(-shiftY);
        }
    }

    public void resumeSliderAnimation() {

        mainPresenter.resumeSliderAnimation();
    }

    public void pauseSliderAnimation() {
        mainPresenter.pauseSliderAnimation();
    }

    @Override
    public void onUpOrCancelMotionEvent(ScrollState scrollState) {
        if (scrollState == ScrollState.STOP) {
            adjustToolbarOnEndOfScroll();
        }
    }

    @Override
    public void onScrollStateChanged(int scrollState) {
        if (scrollState == AbsListView.OnScrollListener.SCROLL_STATE_IDLE) {
            adjustToolbarOnEndOfScroll();
        }
    }

    private void adjustToolbarOnEndOfScroll() {
        float shiftY = -title_container_RL.getTranslationY();

        boolean shouldHide = shiftY > titleMinHeight && oldScrollY >= titleMaxHeight;

        hideToolBarContainer(shouldHide);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        mainPresenter.onActivityResult(requestCode, resultCode, data);
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    protected void onPause() {
        System.out.println("on pause");
        mainPresenter.onPause();
        super.onPause();
    }

    @Override
    public void onRestart() {
        super.onRestart();
        resumeSliderAnimation();
        toolbar.postDelayed(() -> {
            toggleArrow(false);
        }, 200);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_dashboard, menu);
        return true;
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        mainPresenter.prepareOptionsMenu(menu);
        return true;
    }

    @Override
    public void onNavigationItemSelected(int position) {
        switch (position) {
            case 0:
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.container, new DashboardFragment())
                        .commit();
                break;
            case 1:
                Fragment fragment = new CatStreamFragment();
                Bundle bundle = new Bundle();
                bundle.putString(CatStreamFragment.STREAM_USER_ID, mainPresenter.getCurrentUserId());
                fragment.setArguments(bundle);
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.container, fragment)
                        .commit();
                break;
            default:
                break;
        }
        mDrawerLayout.postDelayed(mDrawerLayout::closeDrawers, 200);
    }

    @Override
    public PagerSlidingTabStrip getSlidingTabStrip() {
        return slidingTabs_PSTS;
    }
}
