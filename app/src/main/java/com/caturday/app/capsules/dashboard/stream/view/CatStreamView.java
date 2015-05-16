package com.caturday.app.capsules.dashboard.stream.view;

import android.support.v7.widget.RecyclerView;

import com.github.ksoichiro.android.observablescrollview.ObservableScrollViewCallbacks;

public interface CatStreamView {

    void initializeRecyclerView(ObservableScrollViewCallbacks listener,
                                       RecyclerView.LayoutManager layoutManager);

    void notifyAdapter();

    void setScrollPosition(int offset);

    int getScrollPosition();

    RecyclerView getRecyclerView();

    CatPostAdapter getAdapter();

    void showEmptyView(boolean showIt, boolean loggedIn);

    void onPageSelected();
}
