package com.caturday.app.capsules.dashboard;

import android.os.Bundle;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.astuetz.PagerSlidingTabStrip;
import com.caturday.app.R;
import com.caturday.app.capsules.common.view.mvp.BaseFragment;
import com.caturday.app.capsules.dashboard.adapter.DashboardPageAdapter;

import java.util.Arrays;
import java.util.List;

import javax.inject.Inject;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

public class DashboardFragment extends BaseFragment implements DashboardView {

    @Inject DashboardPresenter dashboardPresenter;

    @InjectView(R.id.dashboard_VP) ViewPager dashboard_VP;
    @InjectView(R.id.swipe_container) SwipeRefreshLayout swipe_container;
    @InjectView(R.id.new_post_B) View newPostFab;

    private PagerSlidingTabStrip slidingTabs_PSTS;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_dashboard, container, false);

        ButterKnife.inject(this, rootView);

        dashboardPresenter.onCreateView(this);

        return rootView;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        swipe_container.postDelayed(() -> {
            dashboardPresenter.onViewCreated();
        }, 100);
    }

    @Override
    public void initializeSwipeContainer(SwipeRefreshLayout.OnRefreshListener listener) {
        swipe_container.setOnRefreshListener(listener);
        swipe_container.setColorSchemeColors(
                getResources().getColor(R.color.primary),
                getResources().getColor(R.color.accent));
    }

    @Override
    public ViewPager getViewPager() {
        return dashboard_VP;
    }

    @Override
    public void hideFAB() {
        newPostFab.postDelayed(() ->
                newPostFab.setAlpha(0f)
                , 400);
    }

    @Override
    public void showFAB() {
        newPostFab.setAlpha(1f);
    }

    @Override
    public void setRefreshing(boolean refreshing) {
        swipe_container.setRefreshing(refreshing);
    }

    @Override
    public void initializePager(FragmentStatePagerAdapter adapter, PagerSlidingTabStrip slidingTabs_PSTS) {

        dashboard_VP.setAdapter(adapter);
        dashboard_VP.setOffscreenPageLimit(5);
        slidingTabs_PSTS.setViewPager(dashboard_VP);
        slidingTabs_PSTS.setTextColor(getResources().getColor(R.color.white));
        slidingTabs_PSTS.setOnPageChangeListener(dashboardPresenter);
    }

    @Override
    public void enableSwipeToRefresh(boolean enabled) {
        if (swipe_container != null) {
            swipe_container.setEnabled(enabled);
        }
    }

    @Override
    protected List<Object> getModules() {
        return Arrays.<Object>asList(new DashboardModule(this));
    }

    @OnClick(R.id.new_post_B)
    public void newPost(){

        dashboardPresenter.createNewPost(newPostFab);
    }

    @Override
    public void onDestroyView() {
        ((DashboardPageAdapter)dashboard_VP.getAdapter()).destroyItems();
        super.onDestroyView();
    }
}
