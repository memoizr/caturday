package com.caturday.app.capsules.profile.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.astuetz.PagerSlidingTabStrip;
import com.bumptech.glide.Glide;
import com.caturday.app.R;
import com.caturday.app.capsules.common.view.mvp.BaseAppCompatActivity;
import com.caturday.app.capsules.dashboard.SlidingTabActivity;
import com.caturday.app.capsules.profile.ProfileModule;

import java.util.Arrays;
import java.util.List;

import javax.inject.Inject;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class ProfileActivity extends BaseAppCompatActivity implements SlidingTabActivity, ProfileView {
    public static final String EXTRA_ID = "server_id";

    @InjectView(R.id.profile_VP) ViewPager profile_VP;
    @InjectView(R.id.sliding_PSTS) PagerSlidingTabStrip sliding_PSTS;
    @InjectView(R.id.cover_image_IV) ImageView coverImageIV;
    @InjectView(R.id.profile_image_IV) ImageView profileImageIV;
    @InjectView(R.id.username_TV) TextView usernameTV;

    @InjectView(R.id.toolbar) Toolbar toolbar;
    @Inject ProfilePresenter profilePresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        ButterKnife.inject(this);

        getWindow().getDecorView().setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);

        profilePresenter.bindView(this);
        profilePresenter.onCreate(this, savedInstanceState);
    }

    @Override
    protected void onRestart() {
        super.onResume();
        profilePresenter.onResume();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        profilePresenter.onActivityResult(requestCode, resultCode, data);
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public List<Object> getModules() {
        return Arrays.asList(ProfileModule.getInstance(this));
    }

    @Override
    public void initializePager(FragmentPagerAdapter adapter, PagerSlidingTabStrip slidingTabs_PSTS) {

        profile_VP.setAdapter(adapter);
        slidingTabs_PSTS.setViewPager(profile_VP);
        slidingTabs_PSTS.setTextColor(getResources().getColor(R.color.white));
   }

    @Override
    public void setProfileImage(String imageUrl) {
        Glide.with(this)
                .load(imageUrl)
                .into(profileImageIV);
    }

    @Override
    public void setCoverImage(String coverImageUrl) {
        Glide.with(this)
                .load(coverImageUrl)
                .placeholder(R.drawable.default_user_profile_cover)
                .into(coverImageIV);
    }

    @Override
    public void setupCollapsibleToolbar(Toolbar toolbar) {
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_white_larger_24dp);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("");

        toolbar.setNavigationOnClickListener(v -> onBackPressed());
    }

    @Override
    public Toolbar getToolbar() {
        return toolbar;
    }

    @Override
    public void setUsername(String string) {
        usernameTV.setText(string);
    }

    @Override
    public PagerSlidingTabStrip getSlidingTabStrip() {
        return sliding_PSTS;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
//       getMenuInflater().inflate(R.menu.menu_profile, menu);
        return false;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        profilePresenter.onDestroy();
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
//        profilePresenter.prepareOptionsMenu(menu);
        return false;
    }

}
