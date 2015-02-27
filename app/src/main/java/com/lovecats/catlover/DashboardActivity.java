package com.lovecats.catlover;

import android.annotation.TargetApi;
import android.graphics.PorterDuff;
import android.graphics.drawable.TransitionDrawable;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.transition.Fade;
import android.transition.Transition;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.ScaleAnimation;
import android.widget.AbsListView;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.astuetz.PagerSlidingTabStrip;
import com.github.ksoichiro.android.observablescrollview.ScrollState;
import com.lovecats.catlover.adapters.DashboardPageAdapter;
import com.lovecats.catlover.data.CatFetcher;
import com.lovecats.catlover.data.CatModel;
import com.lovecats.catlover.views.CollapsibleView;
import com.squareup.picasso.Picasso;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import greendao.CatImage;

public class DashboardActivity extends ActionBarActivity
        implements CatStreamFragment.Callback, ViewPager.OnPageChangeListener,
        SwipeRefreshLayout.OnRefreshListener, CatFetcher.FetcherCallback {
    @InjectView(R.id.toolbar) Toolbar toolbar;
    @InjectView(R.id.dashboard_VP) ViewPager dashboard_VP;
    @InjectView(R.id.title_container_RL) RelativeLayout title_container_RL;
    @InjectView(R.id.sliding_PSTS) PagerSlidingTabStrip slidingTabs_PSTS;
    @InjectView(R.id.dashboard_background_0_IV) ImageView dashboard_background_0_IV;
    @InjectView(R.id.dashboard_image_container_V) View dashboard_image_container;
    @InjectView(R.id.swipe_container) SwipeRefreshLayout swipe_container;

    private DashboardPageAdapter pagerAdapter;
    private CollapsibleView collapsibleView;
    private int oldScrollY;
    private int titleMaxHeight;
    private int titleMinHeight;
    private TransitionDrawable transitionDrawable;
    private boolean transparent = true;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        ButterKnife.inject(this);


        swipe_container.setOnRefreshListener(this);
        swipe_container.setColorSchemeColors(
                getResources().getColor(R.color.primary),
                getResources().getColor(R.color.accent));

        setupPager();
        setupCollapsibleToolbar();

        dashboard_background_0_IV.setColorFilter(getResources().getColor(R.color.primary_dark), PorterDuff.Mode.SCREEN);

        String url = CatModel.getCatImageForId(this, (long) Math.ceil(40 * Math.random())).getUrl();
        Picasso.with(this).load(url).into(dashboard_background_0_IV);
        setupActivityTransitions();
        cycleBackgroundImage();
    }

    private static boolean supportsAPI(int targetAPI) {
        int currentAPIVersion = Build.VERSION.SDK_INT;
        if (currentAPIVersion >= targetAPI)
            return true;
        else
            return false;
    }

    @Override
    public void onRefresh() {
        ((CatStreamFragment)pagerAdapter.getItem(0)).fetchCats();
    }

    private void setupCollapsibleToolbar() {
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        collapsibleView = new CollapsibleView(this);
        toolbar.addView(collapsibleView);

        titleMaxHeight = collapsibleView.getMaxHeight();
        titleMinHeight = collapsibleView.getMinHeight();

        transitionDrawable = (TransitionDrawable) title_container_RL.getBackground();
    }

    private void setupPager() {
        pagerAdapter = new DashboardPageAdapter(getSupportFragmentManager());
        dashboard_VP.setAdapter(pagerAdapter);

        slidingTabs_PSTS.setViewPager(dashboard_VP);
        slidingTabs_PSTS.setTextColor(getResources().getColor(R.color.white));
        slidingTabs_PSTS.setOnPageChangeListener(this);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    private void setupActivityTransitions() {
        if (supportsAPI(Build.VERSION_CODES.KITKAT)) {
            Transition fade = new Fade();
            fade.excludeTarget(android.R.id.statusBarBackground, true);
            fade.excludeTarget(android.R.id.navigationBarBackground, true);

            if (supportsAPI(Build.VERSION_CODES.LOLLIPOP)) {
                getWindow().setExitTransition(fade);
                getWindow().setEnterTransition(fade);
            }
        }
    }

    private void cycleBackgroundImage() {
        String url = CatModel.getCatImageForId(this, (long) Math.ceil(40 * Math.random())).getUrl();
        Picasso.with(this).load(url).into(dashboard_background_0_IV);
        animateBackgroundImage(dashboard_background_0_IV);
    }

    private void animateBackgroundImage(ImageView view){
        AnimationSet zoomIn = new AnimationSet(true);
        view.setPivotY(1);
        view.setPivotX(0);
        float rand = (float) (Math.random()/1.3) + 1;
        float randAfter = (float) (Math.random()/1.3) + 1;

        ScaleAnimation mScale = new ScaleAnimation(
                rand,
                randAfter,
                rand,
                randAfter,
                ScaleAnimation.RELATIVE_TO_PARENT,
                (float) Math.random(),
                ScaleAnimation.RELATIVE_TO_PARENT,
                (float) Math.random());
        mScale.setDuration(15000);
        zoomIn.addAnimation(mScale);
        zoomIn.setFillBefore(true);
        zoomIn.setFillAfter(true);
        zoomIn.setFillEnabled(true);

        mScale.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                cycleBackgroundImage();
            }

            @Override
            public void onAnimationRepeat(Animation animation) {}
        });

        view.startAnimation(zoomIn);
    }

    // Hide profile by scrolling
    @Override
    public void onScroll(Fragment fragment, int scrollY,
                         boolean firstScroll, boolean dragging) {

        if (scrollY == 0) {
            swipe_container.setEnabled(true);
        } else {
            swipe_container.setEnabled(false);
        }

        dashboard_image_container.setTranslationY(-(float) (scrollY * 0.8));

        title_container_RL.animate().cancel();

        // Note: positive delta means scrolling up
        int scrollDelta = scrollY - oldScrollY;
        oldScrollY = scrollY;

        if (scrollY < titleMaxHeight - titleMinHeight) {
            collapsibleView.setCollapseLevel(
                    Math.max(1f - scrollY / (float) (titleMaxHeight - titleMinHeight), 0));
            if(!transparent) {
                transitionDrawable.reverseTransition(200);
                title_container_RL.setBackground(getResources().getDrawable(R.drawable.solid_transparent));
                transparent = true;
            }
            title_container_RL.setTranslationY(0);

        } else if (scrollY < titleMaxHeight && scrollDelta > 0) {
            collapsibleView.setCollapseLevel(0);

            float shiftY = (scrollY - titleMaxHeight + titleMinHeight);
            shiftY = Math.min(Math.max(shiftY, 0), 208);
            title_container_RL.setTranslationY(-shiftY);

        } else {
            collapsibleView.setCollapseLevel(0);

            if(transparent && scrollY > titleMaxHeight + titleMinHeight) {
                transitionDrawable.startTransition(200);
                transparent = false;
                title_container_RL.setBackground(getResources().getDrawable(R.drawable.solid_primary));
                title_container_RL.setElevation(5);
            }

            // To avoid confusion about sign, use shiftY instead of transitionY.
            // shiftY has same sign as scrollY and always positive or 0.
            float shiftY = -title_container_RL.getTranslationY();
            shiftY = Math.min(Math.max(shiftY + scrollDelta, 0), 208);
            title_container_RL.setTranslationY(-shiftY);
        }
    }

    @Override
    public void onUpOrCancelMotionEvent(Fragment fragment, ScrollState scrollstate) {
        adjustToolbarOnEndOfScroll();
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
            targetShiftY = titleMinHeight + slidingTabs_PSTS.getHeight();
        }

        if(transparent && shiftY > 0) {
            transitionDrawable.startTransition(200);
            title_container_RL.setBackground(getResources().getDrawable(R.drawable.solid_primary));
            transparent = false;
        }
        title_container_RL.animate().cancel();
        title_container_RL.animate()
                .translationY(-targetShiftY).setDuration(200).start();
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
        swipe_container.setEnabled(false);

    }

    @Override
    public void onPageSelected(int position) {
        swipe_container.setEnabled(true);
        if (position == 0) {
            ((CatStreamFragment)pagerAdapter.getItem(1)).setScrollPosition(titleMinHeight);
            onScroll(pagerAdapter.getItem(1), titleMinHeight, false, false);
        } else {
            ((CatStreamFragment)pagerAdapter.getItem(0)).setScrollPosition(titleMinHeight);
            onScroll(pagerAdapter.getItem(1), titleMinHeight, false, false);
        }
    }

    @Override
    public void onPageScrollStateChanged(int state) {
        float targetShiftY = 0;

        title_container_RL.animate().cancel();
        title_container_RL.animate()
                .translationY(-targetShiftY).setDuration(200).start();
    }

    @Override
    public void onFetchComplete(List<CatImage> catImages) {
        System.out.println("fetch complete from dashboard");
        swipe_container.setRefreshing(false);

    }
}
