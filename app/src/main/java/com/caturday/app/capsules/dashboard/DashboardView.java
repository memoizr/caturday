package com.caturday.app.capsules.dashboard;

import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.SwipeRefreshLayout;

import com.astuetz.PagerSlidingTabStrip;

public interface DashboardView {

    void setRefreshing(boolean refreshing);

    void enableSwipeToRefresh(boolean enabled);

    void initializePager(FragmentStatePagerAdapter adapter, PagerSlidingTabStrip pager);

    void initializeSwipeContainer(SwipeRefreshLayout.OnRefreshListener listener);

    ViewPager getViewPager();

    void hideFAB();

    void showFAB();
}
