package com.lovecats.catlover.capsules.main.view;

import android.animation.Animator;
import android.app.Activity;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.widget.AbsListView;
import android.widget.RelativeLayout;

import com.astuetz.PagerSlidingTabStrip;
import com.daimajia.slider.library.SliderLayout;
import com.github.ksoichiro.android.observablescrollview.ScrollState;
import com.lovecats.catlover.R;
import com.lovecats.catlover.capsules.common.listener.ScrollEventListener;
import com.lovecats.catlover.capsules.common.view.views.CollapsibleView;
import com.lovecats.catlover.capsules.dashboard.DashboardFragment;
import com.lovecats.catlover.capsules.dashboard.SlidingTabActivity;
import com.lovecats.catlover.capsules.drawer.view.DrawerActivity;
import com.lovecats.catlover.capsules.drawer.view.NavigationFragment;
import com.lovecats.catlover.capsules.favorites.view.FavoritesFragment;
import com.lovecats.catlover.capsules.main.MainModule;
import com.lovecats.catlover.capsules.main.presenter.MainPresenter;
import com.lovecats.catlover.util.animation.ImageAnimation;
import com.lovecats.catlover.util.helper.AnimationHelper;
import com.lovecats.catlover.util.helper.DrawerArrowHelper;
import com.lovecats.catlover.util.interpolators.HyperAccelerateDecelerateInterpolator;
import com.lovecats.catlover.util.interpolators.HyperTanAccelerateInterpolator;
import com.lovecats.catlover.util.interpolators.HyperTanDecelerateInterpolator;

import java.util.Arrays;
import java.util.List;

import javax.inject.Inject;

import butterknife.ButterKnife;
import butterknife.InjectView;
import lombok.Getter;

public class MainActivity extends DrawerActivity implements ScrollEventListener,
        SlidingTabActivity, MainView {

    public PagerSlidingTabStrip slidingTabs;
    @Getter private int oldScrollY;

    @Inject CollapsibleView collapsibleView;
    @Inject DashboardFragment dashboardFragment;
    @Inject FavoritesFragment favoritesFragment;
    @Inject MainPresenter mainPresenter;
    @Inject NavigationFragment navigationFragment;

    @Getter @InjectView(R.id.slider) SliderLayout sliderLayout;
    @Getter @InjectView(R.id.toolbar) Toolbar toolbar;
    @InjectView(R.id.main_container_V) DrawerLayout mDrawerLayout;
    @InjectView(R.id.sliding_PSTS) PagerSlidingTabStrip slidingTabs_PSTS;
    @InjectView(R.id.status_bar_scrim) View status_bar_scrim;
    @InjectView(R.id.title_container_RL) RelativeLayout title_container_RL;
    @InjectView(R.id.reveal_V) View reveal;
    private ActionBarDrawerToggle mDrawerToggle;
    private ImageAnimation backgroundImageAnimation;
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
    public void onRefreshCompleted() {
        System.out.print("heyyyyyyyyyyyy");
        reveal().addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animation) {
                fade();
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });
    }

    public void pauseSliderAnimation() {
        mainPresenter.pauseSliderAnimation();
    }

    public void resumeSliderAnimation() {

        mainPresenter.resumeSliderAnimation();
    }

    private Animator reveal() {
        // get the center for the clipping circle
        int cx = reveal.getWidth() / 2;
        int cy = reveal.getTop() + 96;

        // get the final radius for the clipping circle
        int finalRadius = (int) Math.sqrt(Math.pow(reveal.getWidth(), 2) + Math.pow(reveal.getHeight(), 2));

        // create the animator for this view (the start radius is zero)
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

        float targetShiftY = 0;
        if (shiftY > titleMinHeight &&
                oldScrollY >= titleMaxHeight) {

            targetShiftY = titleCollapsed;
        }

        if (transparent && shiftY > 0) {
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
    public void onRestart() {
        super.onRestart();
        resumeSliderAnimation();
        toolbar.postDelayed(()-> {
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
                        .replace(R.id.container, dashboardFragment)
                        .commit();
                break;
            case 1:
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.container, favoritesFragment)
                        .commit();
                break;
            default:
                break;
        }
    }

    @Override
    public PagerSlidingTabStrip getSlidingTabStrip() {
        return slidingTabs_PSTS;
    }
}
