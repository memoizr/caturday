package com.lovecats.catlover.capsules.dashboard.stream.view;

import android.support.v7.widget.RecyclerView;

import com.github.ksoichiro.android.observablescrollview.ObservableScrollViewCallbacks;

/**
 * Created by user on 29/03/15.
 */
public interface CatStreamView {

    void initializeRecyclerView(ObservableScrollViewCallbacks listener,
                                       RecyclerView.LayoutManager layoutManager);

    void notifyAdapter();

    void setAdapter(RecyclerView.Adapter adapter);

    RecyclerView.Adapter getAdapter();
}
