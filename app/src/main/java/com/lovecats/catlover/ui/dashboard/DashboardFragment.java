package com.lovecats.catlover.ui.dashboard;

import android.os.Bundle;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.astuetz.PagerSlidingTabStrip;
import com.lovecats.catlover.R;
import com.lovecats.catlover.ui.common.BaseFragment;
import com.lovecats.catlover.ui.main.MainActivity;

import java.util.Arrays;
import java.util.List;

import javax.inject.Inject;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class DashboardFragment extends BaseFragment implements DashboardView {

    @Inject DashboardPresenter dashboardPresenter;

    @InjectView(R.id.dashboard_VP) ViewPager dashboard_VP;
    @InjectView(R.id.swipe_container) SwipeRefreshLayout swipe_container;

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
    public void initializeSwipeContainer(SwipeRefreshLayout.OnRefreshListener listener) {
        swipe_container.setOnRefreshListener(listener);
        swipe_container.setColorSchemeColors(
                getResources().getColor(R.color.primary),
                getResources().getColor(R.color.accent));
    }

    @Override
    public void setRefreshing(boolean refreshing) {
        swipe_container.setRefreshing(refreshing);
    }

    @Override
    public void initializePager(FragmentStatePagerAdapter adapter, PagerSlidingTabStrip slidingTabs_PSTS) {

        dashboard_VP.setAdapter(adapter);
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
}
