package com.lovecats.catlover.ui.stream.view;

import android.support.v7.widget.RecyclerView;

import com.github.ksoichiro.android.observablescrollview.ObservableScrollViewCallbacks;

import java.util.Collection;

import greendao.CatPost;

/**
 * Created by user on 29/03/15.
 */
public interface CatStreamView {

    public void initializeRecyclerView(ObservableScrollViewCallbacks listener,
                                       RecyclerView.LayoutManager layoutManager);

    public void notifyAdapter();

    public void setAdapter(RecyclerView.Adapter adapter);

    public RecyclerView.Adapter getAdapter();
}