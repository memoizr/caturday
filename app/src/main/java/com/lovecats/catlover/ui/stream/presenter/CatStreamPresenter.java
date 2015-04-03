package com.lovecats.catlover.ui.stream.presenter;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.widget.AbsListView;

import com.github.ksoichiro.android.observablescrollview.ObservableScrollViewCallbacks;

/**
 * Created by user on 29/03/15.
 */
public abstract class CatStreamPresenter implements ObservableScrollViewCallbacks {

    abstract public void onAttach(Activity activity);

    abstract public void onViewCreated();

    abstract public void setAdapterByType(String streamType);

    abstract public void onScrollStateChanged(int scrollState);

    abstract public void loadMore(int page, int totalItems);

//    abstract public void onScroll(Fragment fragment, int scrollY, boolean firstScroll, boolean dragging);

//    abstract public void onUpOrCancelMotionEvent(Fragment fragment, ScrollState scrollState);
}
