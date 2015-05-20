package com.caturday.app.capsules.dashboard;

import android.content.Intent;

import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.CardView;
import android.view.View;
import android.widget.Toast;

import com.astuetz.PagerSlidingTabStrip;
import com.caturday.app.capsules.common.events.OnPageSelectedEvent;
import com.caturday.app.capsules.common.events.OnPagerScrolledEvent;
import com.caturday.app.capsules.common.events.OnPostResult;
import com.caturday.app.capsules.common.events.StreamRefreshCompletedEvent;
import com.caturday.app.capsules.common.events.StreamRefreshedEvent;
import com.caturday.app.capsules.common.events.navigation.OnNavigationItemShownEvent;
import com.caturday.app.capsules.dashboard.adapter.DashboardPageAdapter;
import com.caturday.app.capsules.common.view.mvp.BaseFragment;
import com.caturday.app.capsules.dashboard.interactor.DashboardInteractor;
import com.caturday.app.capsules.newpost.view.NewPostActivity;
import com.squareup.otto.Bus;
import com.squareup.otto.Subscribe;

public class DashboardPresenterImpl extends DashboardPresenter {

    private final Bus eventBus;
    private final DashboardInteractor interactor;
    private DashboardView dashboardView;
    private FragmentActivity activity;
    private DashboardPageAdapter adapter;
    private PagerSlidingTabStrip pager;

    public DashboardPresenterImpl(DashboardView dashboardView,
                                  DashboardInteractor interactor, Bus eventBus){
        this.dashboardView = dashboardView;
        this.interactor = interactor;
        this.eventBus = eventBus;

        eventBus.register(this);
    }

    @Subscribe
    public void onRefreshCompleted(StreamRefreshCompletedEvent event) {
        dashboardView.setRefreshing(false);
    }

    @Subscribe
    public void onPostResult(OnPostResult event) {
        dashboardView.showFAB();
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
    public void createNewPost(View view) {
        if (interactor.isUserLoggedIn()) {
            Intent intent = new Intent(activity, NewPostActivity.class);
            intent.putExtra(NewPostActivity.EXTRA_ORIGIN_LEFT, view.getLeft());
            intent.putExtra(NewPostActivity.EXTRA_ORIGIN_TOP, view.getTop());
            intent.putExtra(NewPostActivity.EXTRA_ORIGIN_WIDTH, view.getWidth());
            intent.putExtra(NewPostActivity.EXTRA_ORIGIN_HEIGHT, view.getHeight());
            intent.putExtra(NewPostActivity.EXTRA_ORIGIN_RADIUS, view.getHeight() / 2);
            activity.startActivityForResult(intent, NewPostActivity.NEW_POST_REQUEST_CODE);
            dashboardView.hideFAB();
        } else {
            Toast.makeText(activity, "Log in to create new posts", Toast.LENGTH_SHORT).show();
        }
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
        eventBus.post(new OnPageSelectedEvent(position));
    }

    @Override
    public void onPageScrollStateChanged(int state) {
        if (state == ViewPager.SCROLL_STATE_DRAGGING) {
            int itemPosition = dashboardView.getViewPager().getCurrentItem();
            eventBus.post(new OnPagerScrolledEvent(itemPosition));
        }
    }

    @Override
    public void onRefresh() {
        eventBus.post(new StreamRefreshedEvent());
    }
}
