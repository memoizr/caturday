package com.caturday.app.capsules.dashboard;

import android.support.v4.view.ViewPager;
import android.support.v4.widget.SwipeRefreshLayout;

import com.caturday.app.capsules.common.view.mvp.BaseFragment;

public abstract class DashboardPresenter implements ViewPager.OnPageChangeListener,
        SwipeRefreshLayout.OnRefreshListener {

    public abstract void onCreateView(BaseFragment fragment);

    public abstract void createNewPost();

    public abstract void onViewCreated();
}