package com.lovecats.catlover;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.annotation.TargetApi;
import android.content.Intent;
import android.graphics.Outline;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.TransitionDrawable;
import android.os.Build;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.transition.ChangeBounds;
import android.transition.ChangeImageTransform;
import android.transition.Explode;
import android.transition.Fade;
import android.transition.Transition;
import android.transition.TransitionSet;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewOutlineProvider;
import android.view.Window;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.BounceInterpolator;
import android.view.animation.ScaleAnimation;
import android.widget.AbsListView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.astuetz.PagerSlidingTabStrip;
import com.github.ksoichiro.android.observablescrollview.ScrollState;
import com.lovecats.catlover.data.CatFetcher;
import com.lovecats.catlover.data.CatModel;
import com.lovecats.catlover.util.HyperAccelerateDecelerateInterpolator;
import com.lovecats.catlover.views.CollapsibleView;
import com.lovecats.catlover.views.SlideShowView;
import com.squareup.picasso.Picasso;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import greendao.CatImage;
import lombok.Getter;
import lombok.Setter;

public class MainActivity extends ActionBarActivity
        implements NavigationFragment.OnFragmentInteractionListener,
        CatStreamFragment.ScrollCallback, CatFetcher.FetcherCallback {

    @InjectView(R.id.toolbar) Toolbar toolbar;
    @InjectView(R.id.title_container_RL) RelativeLayout title_container_RL;
    @InjectView(R.id.slide_show_V) SlideShowView slide_show_V;
    @InjectView(R.id.sliding_PSTS) PagerSlidingTabStrip slidingTabs_PSTS;
    @InjectView(R.id.main_container_V) DrawerLayout mDrawerLayout;
    @InjectView(R.id.status_bar_scrim) View status_bar_scrim;
    @InjectView(R.id.new_post_B) ImageButton new_post_B;

    private CollapsibleView collapsibleView;

    private int titleMaxHeight;
    private int titleMinHeight;
    @Getter
    private int oldScrollY;
    private TransitionDrawable transitionDrawable;
    private boolean transparent = true;
    private boolean statusTransparent = true;
    public PagerSlidingTabStrip slidingTabs;
    private ActionBarDrawerToggle mDrawerToggle;
    private DashboardFragment dashboardFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.inject(this);

        dashboardFragment = new DashboardFragment();
        slidingTabs = slidingTabs_PSTS;

        getWindow().getDecorView().setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);

        setUpFragments(savedInstanceState);

        setupCollapsibleToolbar();
        setDrawer();
        setUpButton();
        new AccelerateDecelerateInterpolator();

//        slide_show_V.postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                slide_show_V.flash();
//            }
//        }, 2000);


    }

    private void setUpFragments(Bundle savedInstanceState) {
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container, dashboardFragment)
                    .commit();
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.left_drawer_V, new NavigationFragment())
                    .commit();
        }
    }

    public void toggleArrow(boolean toggle) {
        ValueAnimator va;
        if (toggle) {
            va = new ObjectAnimator().ofFloat(0, 1);
        } else {
            va = new ObjectAnimator().ofFloat(1, 0);
        }
        va.setDuration(300);
        va.setInterpolator(new AccelerateDecelerateInterpolator());
        va.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                mDrawerToggle.onDrawerSlide(mDrawerLayout, Float.parseFloat(animation.getAnimatedValue().toString()));
            }
        });
        va.start();
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    private void setUpButton() {
        ViewOutlineProvider viewOutlineProvider = new ViewOutlineProvider() {
            @Override
            public void getOutline(View view, Outline outline) {
                int size = getResources().getDimensionPixelSize(R.dimen.fab_size);
                outline.setOval(0, 0, size, size);
            }
        };

        new_post_B.setOutlineProvider(viewOutlineProvider);
        new_post_B.setClipToOutline(true);

        Drawable image = getResources().getDrawable(R.drawable.ic_add_white_48dp);
        new_post_B.setImageDrawable(image);
    }

    public void setDrawer() {
        mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout, toolbar, R.string.action_settings, R.string.action_login);
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

    private void setupCollapsibleToolbar() {

        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("");

        collapsibleView = new CollapsibleView(this);
        toolbar.addView(collapsibleView);

        titleMaxHeight = collapsibleView.getMaxHeight();
        titleMinHeight = collapsibleView.getMinHeight();

        transitionDrawable = (TransitionDrawable) title_container_RL.getBackground();

        toolbar.setOnMenuItemClickListener(
                new Toolbar.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        toggleArrow(true);
                        slide_show_V.animationPause();
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

    @Override
    public void onFragmentInteraction(int position) {
        switch (position) {
            case 0:
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.container, new DashboardFragment())
                        .commit();
                break;
            default:
                break;
        }
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    private void setupActivityTransitions() {
        if (supportsAPI(Build.VERSION_CODES.KITKAT)) {
            Transition fade = new Fade();
            fade.excludeTarget(android.R.id.statusBarBackground, true);
            fade.excludeTarget(android.R.id.navigationBarBackground, true);
            fade.excludeTarget(title_container_RL, true);

            if (supportsAPI(Build.VERSION_CODES.LOLLIPOP)) {
                getWindow().setExitTransition(fade);
                getWindow().setEnterTransition(fade);
            }
        }
    }

    private static boolean supportsAPI(int targetAPI) {
        int currentAPIVersion = Build.VERSION.SDK_INT;
        if (currentAPIVersion >= targetAPI)
            return true;
        else
            return false;
    }

    public void animateTitleContainer(float targetShiftY) {
        title_container_RL.animate().cancel();
        title_container_RL
                .animate()
                .translationY(-targetShiftY)
                .setDuration(300)
                .setInterpolator(new AccelerateDecelerateInterpolator())
                .start();
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
                animateBackground(title_container_RL,
                        getResources().getColor(R.color.primary),
                        getResources().getColor(R.color.primary_transparent));
            }  if (!statusTransparent) {

                animateBackground(status_bar_scrim,
                        getResources().getColor(R.color.primary),
                        getResources().getColor(R.color.primary_transparent));
                transparent = statusTransparent = true;

                slide_show_V.animationStart();
            }
            title_container_RL.setTranslationY(0);

        } else if (scrollY < titleMaxHeight && scrollDelta > 0) {
            collapsibleView.setCollapseLevel(0);
            if (statusTransparent) {
                animateBackground(status_bar_scrim,
                        getResources().getColor(R.color.primary_transparent),
                        getResources().getColor(R.color.primary));
                statusTransparent = false;
            }

            float shiftY = (scrollY - titleMaxHeight + titleMinHeight);
            shiftY = Math.min(Math.max(shiftY, 0), 208);
            title_container_RL.setTranslationY(-shiftY);

        } else {
            collapsibleView.setCollapseLevel(0);

            if (transparent && scrollY > titleMaxHeight + titleMinHeight) {
                animateBackground(title_container_RL,
                        getResources().getColor(R.color.primary_transparent),
                        getResources().getColor(R.color.primary));

                System.out.println("paused");
                slide_show_V.animationPause();
                transparent = false;
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

        if (transparent && shiftY > 0) {
            animateBackground(title_container_RL,
                    getResources().getColor(R.color.primary_transparent),
                    getResources().getColor(R.color.primary));
            System.out.println("paused");
            slide_show_V.animationPause();
            transparent = false;
        }
        title_container_RL.animate().cancel();
        title_container_RL
                .animate()
                .translationY(-targetShiftY)
                .setDuration(300)
                .setInterpolator(new AccelerateDecelerateInterpolator())
                .start();
    }

    private void animateBackground(View view, int colorFrom, int colorTo) {
        final View mView = view;
        final ValueAnimator va = ObjectAnimator.ofArgb(
                colorFrom,
                colorTo);
        va.setDuration(300);
        va.addUpdateListener(new ValueAnimator.AnimatorUpdateListener()

                             {
                                 @Override
                                 public void onAnimationUpdate (ValueAnimator animation){
                                     mView.setBackgroundColor((Integer) animation.getAnimatedValue());
                                 }
                             }

        );
        va.start();
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_dashboard, menu);
        return true;
    }

    @Override
    public void onFetchComplete(List<CatImage> catImages) {
        dashboardFragment.swipe_container.setRefreshing(false);
        slide_show_V.flash();
    }

    @Override
    public void onResume() {
        super.onResume();
        slide_show_V.animationResume();
        toggleArrow(false);
    }

    @OnClick(R.id.new_post_B)
    public void clickNewPost() {
        Intent intent = new Intent(this, NewPostActivity.class);
        startActivity(intent);
    }
}
