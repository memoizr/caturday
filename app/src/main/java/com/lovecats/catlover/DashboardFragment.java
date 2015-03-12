package com.lovecats.catlover;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.astuetz.PagerSlidingTabStrip;
import com.lovecats.catlover.adapters.DashboardPageAdapter;
import com.lovecats.catlover.data.CatFetcher;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import greendao.CatImage;

public class DashboardFragment extends Fragment
        implements ViewPager.OnPageChangeListener,
        SwipeRefreshLayout.OnRefreshListener {
    @InjectView(R.id.dashboard_VP) ViewPager dashboard_VP;
    @InjectView(R.id.swipe_container) SwipeRefreshLayout swipe_container;
    private PagerSlidingTabStrip slidingTabs_PSTS;

    private DashboardPageAdapter pagerAdapter;
    private int currentScrollPosition;
    private CatStreamFragment otherCatStreamFragment;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        View rootView =  inflater.inflate(R.layout.fragment_dashboard, container, false);

        ButterKnife.inject(this, rootView);
        slidingTabs_PSTS = ((MainActivity)getActivity()).slidingTabs;


        swipe_container.setOnRefreshListener(this);
        swipe_container.setColorSchemeColors(
                getResources().getColor(R.color.primary),
                getResources().getColor(R.color.accent));

        setupPager();

        return rootView;
    }

    private void setupPager() {
        pagerAdapter = new DashboardPageAdapter(getActivity().getSupportFragmentManager());
        dashboard_VP.setAdapter(pagerAdapter);

        slidingTabs_PSTS.setViewPager(dashboard_VP);
        slidingTabs_PSTS.setTextColor(getResources().getColor(R.color.white));
        slidingTabs_PSTS.setOnPageChangeListener(this);
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
        swipe_container.setEnabled(false);
    }

    private int selectedPage = 0;

    @Override
    public void onPageSelected(int position) {
        selectedPage = position;
        swipe_container.setEnabled(true);
    }

    public void enableSwipeToRefresh(boolean toggle){
        swipe_container.setEnabled(toggle);
    }


    @Override
    public void onPageScrollStateChanged(int state) {
        float targetShiftY = 0;
        CatStreamFragment catStreamFragment;
        int oldScrollY = ((MainActivity)getActivity()).getOldScrollY();

        if (state == 1) {
            if (selectedPage == 0) {
                catStreamFragment = ((CatStreamFragment) pagerAdapter.getItem(0));
                otherCatStreamFragment = ((CatStreamFragment) pagerAdapter.getItem(1));
                if (catStreamFragment.getScrollPosition() != 0 || oldScrollY == 0) {
                    currentScrollPosition = catStreamFragment.getScrollPosition();
                }
                if (currentScrollPosition > 224) {
                    currentScrollPosition = 224;
                    ((MainActivity)getActivity()).animateTitleContainer(targetShiftY);

                } else {
                    ((MainActivity)getActivity())
                            .onScroll(otherCatStreamFragment, currentScrollPosition, false, false);
                }

            } else {
                catStreamFragment = ((CatStreamFragment) pagerAdapter.getItem(1));
                otherCatStreamFragment = ((CatStreamFragment) pagerAdapter.getItem(0));
                if (catStreamFragment.getScrollPosition() != 0 || oldScrollY == 0) {
                    currentScrollPosition = catStreamFragment.getScrollPosition();
                }
                if (currentScrollPosition > 224) {
                    currentScrollPosition = 224;

                    ((MainActivity)getActivity()).animateTitleContainer(targetShiftY);
                } else {
                    ((MainActivity)getActivity())
                            .onScroll(otherCatStreamFragment, currentScrollPosition, false, false);
                }
            }
            otherCatStreamFragment.setScrollPosition(currentScrollPosition);
        }
    }

    @Override
    public void onRefresh() {
//        ((CatStreamFragment)pagerAdapter.getItem(0)).fetchCats();
    }

}
