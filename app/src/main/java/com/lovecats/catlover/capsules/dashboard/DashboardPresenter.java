package com.lovecats.catlover.capsules.dashboard;

import android.support.v4.view.ViewPager;
import android.support.v4.widget.SwipeRefreshLayout;

import com.lovecats.catlover.capsules.common.BaseFragment;

/**
 * Created by user on 29/03/15.
 */
public abstract class DashboardPresenter implements ViewPager.OnPageChangeListener,
        SwipeRefreshLayout.OnRefreshListener {

    public abstract void onCreateView(BaseFragment fragment);
}
