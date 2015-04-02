package com.lovecats.catlover.ui.dashboard;

import android.app.Activity;

import com.astuetz.PagerSlidingTabStrip;
import com.lovecats.catlover.ui.dashboard.adapter.DashboardPageAdapter;
import com.lovecats.catlover.ui.common.BaseFragment;

/**
 * Created by user on 29/03/15.
 */
public class DashboardPresenterImpl extends DashboardPresenter {

    private DashboardView dashboardView;

    public DashboardPresenterImpl(DashboardView dashboardView){
        this.dashboardView = dashboardView;
    }

    @Override
    public void onCreateView(BaseFragment fragment) {
        Activity activity = fragment.getActivity();
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
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
        dashboardView.enableSwipeToRefresh(false);
    }

    @Override
    public void onPageSelected(int position) {
        dashboardView.enableSwipeToRefresh(true);
    }

    @Override
    public void onPageScrollStateChanged(int state) {
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
//        CatPostFetcher.getSession().fetchCatPosts(new CatPostFetcher.CatPostFetcherCallbacks() {
//
//            @Override
//            public void onSuccess(List<CatPostModel> catPostModels) {
//                swipe_container.setRefreshing(false);
//                ((MainActivity) getActivity()).doneSlideshow();
//            }
//        });
    }
}
