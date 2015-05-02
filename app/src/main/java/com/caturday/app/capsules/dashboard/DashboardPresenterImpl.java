package com.caturday.app.capsules.dashboard;

import android.content.Intent;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;

import com.astuetz.PagerSlidingTabStrip;
import com.caturday.app.capsules.common.events.OnPageScrolledEvent;
import com.caturday.app.capsules.common.events.StreamRefreshCompletedEvent;
import com.caturday.app.capsules.common.events.StreamRefreshedEvent;
import com.caturday.app.capsules.common.events.navigation.OnNavigationItemShownEvent;
import com.caturday.app.capsules.dashboard.adapter.DashboardPageAdapter;
import com.caturday.app.capsules.common.view.mvp.BaseFragment;
import com.caturday.app.capsules.newpost.view.NewPostActivity;
import com.squareup.otto.Bus;
import com.squareup.otto.Subscribe;

public class DashboardPresenterImpl extends DashboardPresenter {

    private final Bus eventBus;
    private DashboardView dashboardView;
    private FragmentActivity activity;
    private DashboardPageAdapter adapter;
    private boolean scrolling;
    private PagerSlidingTabStrip pager;

    public DashboardPresenterImpl(DashboardView dashboardView, Bus eventBus){
        this.dashboardView = dashboardView;
        this.eventBus = eventBus;

        eventBus.register(this);
    }

    @Subscribe
    public void onRefreshCompleted(StreamRefreshCompletedEvent event) {
        dashboardView.setRefreshing(false);
    }

    @Override
    public void onCreateView(BaseFragment fragment) {
        activity = fragment.getActivity();
        if (activity instanceof SlidingTabActivity) {

            adapter = new DashboardPageAdapter(fragment.getFragmentManager());
            pager = ((SlidingTabActivity) activity).getSlidingTabStrip();
            dashboardView.initializePager(adapter, pager);
        } else {
            throw new RuntimeException("Parent activity must implement SlidingTabActivity");
        }
        dashboardView.initializeSwipeContainer(this);
    }


    @Override
    public void createNewPost() {
        Intent intent = new Intent(activity, NewPostActivity.class);
        activity.startActivity(intent);
    }

    @Override
    public void onViewCreated() {
        eventBus.post(new OnNavigationItemShownEvent(OnNavigationItemShownEvent.ITEM_DASHBOARD));
    }


    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
        dashboardView.enableSwipeToRefresh(false);
    }

    @Override
    public void onPageSelected(int position) {
        dashboardView.enableSwipeToRefresh(true);
    }

    @Override
    public void onPageScrollStateChanged(int state) {
        if (state == ViewPager.SCROLL_STATE_DRAGGING) {
            int itemPosition = dashboardView.getViewPager().getCurrentItem();
            eventBus.post(new OnPageScrolledEvent(itemPosition, -1));
        }
    }

    @Override
    public void onRefresh() {
        eventBus.post(new StreamRefreshedEvent());
    }
}
