package com.caturday.app.capsules.dashboard.stream.view;

import android.support.v7.widget.RecyclerView;

import com.github.ksoichiro.android.observablescrollview.ObservableScrollViewCallbacks;

public interface CatStreamView {

    void initializeRecyclerView(ObservableScrollViewCallbacks listener,
                                       RecyclerView.LayoutManager layoutManager);

    void notifyAdapter();

    void setAdapter(RecyclerView.Adapter adapter);

    void setScrollPosition(int offset);

    int getScrollPosition();

    RecyclerView getRecyclerView();

    RecyclerView.Adapter getAdapter();
}
