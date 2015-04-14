package com.lovecats.catlover.capsules.profile.view;

import android.os.Bundle;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;

import com.astuetz.PagerSlidingTabStrip;
import com.lovecats.catlover.R;
import com.lovecats.catlover.capsules.common.BaseActionBarActivity;
import com.lovecats.catlover.capsules.profile.ProfileModule;

import java.util.Arrays;
import java.util.List;

import javax.inject.Inject;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

public class ProfileActivity extends BaseActionBarActivity implements ProfileView {
//    @InjectView(R.id.logout) Button logout;
    @InjectView(R.id.profile_VP) ViewPager profile_VP;
//    @InjectView(R.id.user_name_ET) EditText user_name_ET;
    @Inject ProfilePresenter profilePresenter;
    @InjectView(R.id.toolbar) Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        ButterKnife.inject(this);

        getWindow().getDecorView().setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);

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
//        slidingTabs_PSTS.setViewPager(dashboard_VP);
//        slidingTabs_PSTS.setTextColor(getResources().getColor(R.color.white));
//        slidingTabs_PSTS.setOnPageChangeListener(dashboardPresenter);
    }

    @Override
    public void initToolbar() {
        toolbar.setTitle("Username");
        setSupportActionBar(toolbar);

        toolbar.setNavigationOnClickListener(v -> onBackPressed());

        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_arrow_back_white_larger_24dp);
    }

    @Override
    public void setUserName(String string) {
//        user_name_ET.setText(string);
    }

//    @OnClick(R.id.logout)
    public void clickLogout() {
        profilePresenter.logout();
    }

    @Override
    public void onPostLogout() {
//        logout.setVisibility(View.GONE);
    }
}
