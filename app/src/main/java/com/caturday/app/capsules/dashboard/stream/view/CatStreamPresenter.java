package com.caturday.app.capsules.dashboard.stream.view;

import android.app.Activity;
import android.view.View;

import com.caturday.app.models.catpost.CatPostEntity;
import com.github.ksoichiro.android.observablescrollview.ObservableScrollViewCallbacks;

public abstract class CatStreamPresenter implements ObservableScrollViewCallbacks {

    abstract public void onAttach(Activity activity);

    abstract public void onViewCreated(String streamType, int position);

    abstract public void setAdapterByType(String streamType);

    abstract public void onScrollStateChanged(int scrollState);

    abstract public void loadMore(int page, int totalItems);

    public abstract void plusOneClicked(String serverId, int position);

    public abstract void openDetails(int i, View view, CatPostEntity catPostEntity, boolean showComments);

//    abstract public void onScroll(Fragment fragment, int scrollY, boolean firstScroll, boolean dragging);

//    abstract public void onUpOrCancelMotionEvent(Fragment fragment, ScrollState scrollState);
}
