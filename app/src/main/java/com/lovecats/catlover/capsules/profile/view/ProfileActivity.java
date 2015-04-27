package com.lovecats.catlover.capsules.profile.view;

import android.os.Bundle;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;

import com.astuetz.PagerSlidingTabStrip;
import com.bumptech.glide.Glide;
import com.lovecats.catlover.R;
import com.lovecats.catlover.capsules.common.BaseActionBarActivity;
import com.lovecats.catlover.capsules.common.view.views.CollapsibleView;
import com.lovecats.catlover.capsules.dashboard.SlidingTabActivity;
import com.lovecats.catlover.capsules.profile.ProfileModule;

import java.util.Arrays;
import java.util.List;

import javax.inject.Inject;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class ProfileActivity extends BaseActionBarActivity implements SlidingTabActivity, ProfileView {
    public static final String EXTRA_ID = "server_id";
    @InjectView(R.id.profile_VP) ViewPager profile_VP;
    @InjectView(R.id.sliding_PSTS) PagerSlidingTabStrip sliding_PSTS;
    @InjectView(R.id.cover_image_IV) ImageView coverImageIV;

    @InjectView(R.id.toolbar) Toolbar toolbar;
    @Inject ProfilePresenter profilePresenter;
    private int titleMaxHeight;
    private int titleMinHeight;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        ButterKnife.inject(this);

        getWindow().getDecorView().setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);

        System.out.println(getIntent().getExtras().get(EXTRA_ID));

        profilePresenter.onCreate(this);
    }

    @Override
    public List<Object> getModules() {
        return Arrays.asList(new ProfileModule(this));
    }

    @Override
    public void showButton(Boolean visible) {
        if (visible) {
//            logout.setVisibility(View.VISIBLE);
        } else {
//            logout.setVisibility(View.GONE);
        }
    }

    @Override
    public void initializePager(FragmentPagerAdapter adapter, PagerSlidingTabStrip slidingTabs_PSTS) {

        profile_VP.setAdapter(adapter);
        slidingTabs_PSTS.setViewPager(profile_VP);
        slidingTabs_PSTS.setTextColor(getResources().getColor(R.color.white));
   }

    @Override
    public void setProfileImage(String imageUrl) {

    }

    @Override
    public void setCoverImage(String coverImageUrl) {
        Glide.with(this).load(coverImageUrl).into(coverImageIV);
    }

    @Override
    public void setupCollapsibleToolbar(Toolbar toolbar) {
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("");

        CollapsibleView collapsibleView = new CollapsibleView(this);

        toolbar.addView(collapsibleView);

        titleMaxHeight = collapsibleView.getMaxHeight();
        titleMinHeight = collapsibleView.getMinHeight();

        toolbar.setNavigationOnClickListener(v -> onBackPressed());

        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_arrow_back_white_larger_24dp);
    }

    @Override
    public Toolbar getToolbar() {
        return toolbar;
    }

    @Override
    public void setUsername(String string) {
//        user_name_ET.setText(string);
    }

    @Override
    public PagerSlidingTabStrip getSlidingTabStrip() {
        return sliding_PSTS;
    }
}
