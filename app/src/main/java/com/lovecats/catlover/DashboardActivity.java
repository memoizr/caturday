package com.lovecats.catlover;

import android.animation.ObjectAnimator;
import android.graphics.PorterDuff;
import android.graphics.drawable.TransitionDrawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.transition.Fade;
import android.transition.Transition;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.ScaleAnimation;
import android.view.animation.TranslateAnimation;
import android.widget.AbsListView;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.astuetz.PagerSlidingTabStrip;
import com.github.ksoichiro.android.observablescrollview.ScrollState;
import com.lovecats.catlover.adapters.DashboardPageAdapter;
import com.lovecats.catlover.data.CatModel;
import com.lovecats.catlover.views.CollapsibleView;
import com.squareup.picasso.Picasso;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by user on 23/02/15.
 */
public class DashboardActivity extends ActionBarActivity implements NewCatsFragment.Callback, ViewPager.OnPageChangeListener{
    @InjectView(R.id.toolbar) Toolbar toolbar;
    @InjectView(R.id.dashboard_VP) ViewPager dashboard_VP;
    @InjectView(R.id.title_container_RL) RelativeLayout title_container_RL;
    @InjectView(R.id.sliding_PSTS) PagerSlidingTabStrip slidingTabs_PSTS;
    @InjectView(R.id.dashboard_background_IV) ImageView dashboard_background_IV;
    @InjectView(R.id.dashboard_image_container_V) View dashboard_image_container;

    private DashboardPageAdapter pagerAdapter;
    private CollapsibleView collapsibleView;
    private int oldScrollY;
    private int titleMaxHeight;
    private int titleMinHeight;
    private TransitionDrawable transitionDrawable;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        ButterKnife.inject(this);

        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Caturday");

        pagerAdapter = new DashboardPageAdapter(getSupportFragmentManager());
        dashboard_VP.setAdapter(pagerAdapter);
        slidingTabs_PSTS.setViewPager(dashboard_VP);
        slidingTabs_PSTS.setTextColor(getResources().getColor(R.color.white));
        slidingTabs_PSTS.setOnPageChangeListener(this);
        String url = CatModel.getCatImageForId(this, (long) Math.floor(20 * Math.random())).getUrl();
        Picasso.with(this).load(url).into(dashboard_background_IV);
        dashboard_background_IV.setColorFilter(getResources().getColor(R.color.primary_dark), PorterDuff.Mode.SCREEN);

        transitionDrawable = (TransitionDrawable) title_container_RL.getBackground();

        collapsibleView = new CollapsibleView(this);
        toolbar.addView(collapsibleView);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        titleMaxHeight = collapsibleView.getMaxHeight();
        titleMinHeight = collapsibleView.getMinHeight();

        Transition fade = new Fade();
        fade.excludeTarget(android.R.id.statusBarBackground, true);
        fade.excludeTarget(android.R.id.navigationBarBackground, true);
        getWindow().setExitTransition(fade);
        getWindow().setEnterTransition(fade);

        AnimationSet zoomIn = new AnimationSet(true);
        dashboard_background_IV.setPivotY(1);
        dashboard_background_IV.setPivotX(0);
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
        zoomIn.setFillAfter(true);
        zoomIn.setFillEnabled(true);

        dashboard_background_IV.startAnimation(zoomIn);

    }
    private boolean transparent = true;

    // Hide profile by scrolling
    @Override
    public void onScroll(Fragment fragment, int scrollY,
                         boolean firstScroll, boolean dragging) {

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
                title_container_RL.setTranslationZ(5);
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

    }

    @Override
    public void onPageSelected(int position) {

    }

    @Override
    public void onPageScrollStateChanged(int state) {
        System.out.println(state);

        float targetShiftY = 0;
        title_container_RL.animate().cancel();
        title_container_RL.animate()
                .translationY(-targetShiftY).setDuration(200).start();
    }
}
