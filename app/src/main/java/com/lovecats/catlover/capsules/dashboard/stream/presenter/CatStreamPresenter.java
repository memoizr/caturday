package com.lovecats.catlover.capsules.dashboard.stream.presenter;

import android.app.Activity;

import com.github.ksoichiro.android.observablescrollview.ObservableScrollViewCallbacks;

public abstract class CatStreamPresenter implements ObservableScrollViewCallbacks {

    abstract public void onAttach(Activity activity);

    abstract public void onViewCreated(String streamType, int position);

    abstract public void setAdapterByType(String streamType);

    abstract public void onScrollStateChanged(int scrollState);

    abstract public void loadMore(int page, int totalItems);

//    abstract public void onScroll(Fragment fragment, int scrollY, boolean firstScroll, boolean dragging);

//    abstract public void onUpOrCancelMotionEvent(Fragment fragment, ScrollState scrollState);
}
