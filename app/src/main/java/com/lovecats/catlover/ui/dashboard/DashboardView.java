package com.lovecats.catlover.ui.dashboard;

import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.widget.SwipeRefreshLayout;

import com.astuetz.PagerSlidingTabStrip;

/**
 * Created by user on 29/03/15.
 */
public interface DashboardView {

    public void setRefreshing(boolean refreshing);

    public void enableSwipeToRefresh(boolean enabled);

    public void initializePager(FragmentStatePagerAdapter adapter, PagerSlidingTabStrip pager);

    public void initializeSwipeContainer(SwipeRefreshLayout.OnRefreshListener listener);
}
