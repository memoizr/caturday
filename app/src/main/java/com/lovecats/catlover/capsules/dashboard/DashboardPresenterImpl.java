package com.lovecats.catlover.capsules.dashboard;

import android.content.Intent;
import android.support.v4.app.FragmentActivity;

import com.astuetz.PagerSlidingTabStrip;
import com.lovecats.catlover.capsules.common.Events.StreamRefreshCompletedEvent;
import com.lovecats.catlover.capsules.common.Events.StreamRefreshedEvent;
import com.lovecats.catlover.capsules.dashboard.adapter.DashboardPageAdapter;
import com.lovecats.catlover.capsules.common.BaseFragment;
import com.lovecats.catlover.capsules.newpost.view.NewPostActivity;
import com.squareup.otto.Bus;
import com.squareup.otto.Subscribe;

public class DashboardPresenterImpl extends DashboardPresenter {

    private final Bus eventBus;
    private DashboardView dashboardView;
    private FragmentActivity activity;

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

            DashboardPageAdapter adapter = new DashboardPageAdapter(fragment.getFragmentManager());
            PagerSlidingTabStrip pager = ((SlidingTabActivity) activity).getSlidingTabStrip();
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
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
        dashboardView.enableSwipeToRefresh(false);
    }

    @Override
    public void onPageSelected(int position) {
        dashboardView.enableSwipeToRefresh(true);
    }

    @Override
    public void onPageScrollStateChanged(int state) {
        // TODO reduce amount of WTF

//        float targetShiftY = 0;
//        int oldScrollY = ((MainActivity) getActivity()).getOldScrollY();

//        if (state == 1) {
//            if (selectedPage == 0) {
//                catStreamFragment = ((CatStreamFragment) pagerAdapter.getItem(0));
//                otherCatStreamFragment = ((CatStreamFragment) pagerAdapter.getItem(1));
//                if (catStreamFragment.getScrollPosition() != 0 || oldScrollY == 0) {
//                    currentScrollPosition = catStreamFragment.getScrollPosition();
//                }
//                if (currentScrollPosition > 224) {
//                    currentScrollPosition = 224;
//                    ((MainActivity)getActivity()).animateTitleContainer(targetShiftY);
//
//                } else {
//                    ((MainActivity)getActivity())
//                            .onScroll(otherCatStreamFragment, currentScrollPosition, false, false);
//                }
//
//            } else {
//                catStreamFragment = ((CatStreamFragment) pagerAdapter.getItem(1));
//                otherCatStreamFragment = ((CatStreamFragment) pagerAdapter.getItem(0));
//                if (catStreamFragment.getScrollPosition() != 0 || oldScrollY == 0) {
//                    currentScrollPosition = catStreamFragment.getScrollPosition();
//                }
//                if (currentScrollPosition > 224) {
//                    currentScrollPosition = 224;
//
//                    ((MainActivity)getActivity()).animateTitleContainer(targetShiftY);
//                } else {
//                    ((MainActivity)getActivity())
//                            .onScroll(otherCatStreamFragment, currentScrollPosition, false, false);
//                }
//            }
//            otherCatStreamFragment.setScrollPosition(currentScrollPosition);
//        }
    }

    @Override
    public void onRefresh() {
        System.out.println("should be working....");
        eventBus.post(new StreamRefreshedEvent());
    }
}
