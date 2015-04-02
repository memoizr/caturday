package com.lovecats.catlover.ui.stream.presenter;

import android.app.Activity;
import android.content.Context;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;

import com.github.ksoichiro.android.observablescrollview.ScrollState;
import com.lovecats.catlover.ui.common.ScrollEventListener;
import com.lovecats.catlover.ui.stream.adapter.CatPostAdapter;
import com.lovecats.catlover.ui.stream.interactor.CatStreamInteractor;
import com.lovecats.catlover.ui.stream.view.CatStreamView;

import java.util.ArrayList;
import java.util.Collection;

import javax.inject.Inject;

import greendao.CatPost;

public class CatStreamPresenterImpl extends CatStreamPresenter {

    private final CatStreamView catStreamView;
    private final CatStreamInteractor catStreamInteractor;
    private ScrollEventListener scrollEventListener;
    private static final String WARN_NO_LISTENER = "No Listeners attached";
    private Context context;

    public CatStreamPresenterImpl(CatStreamView catStreamView, CatStreamInteractor catStreamInteractor) {
        this.catStreamView = catStreamView;
        this.catStreamInteractor = catStreamInteractor;
    }

    @Override
    public void onScrollChanged(int i, boolean firstScroll, boolean dragging) {
        scrollEventListener.onScrollChanged(i, dragging);
    }

    @Override
    public void onDownMotionEvent() {

    }

    @Override
    public void onUpOrCancelMotionEvent(ScrollState scrollState) {
        scrollEventListener.onUpOrCancelMotionEvent(scrollState);
    }

    @Override
    public void onAttach(Activity activity) {

        this.context = activity;

        if (activity instanceof ScrollEventListener) {
            scrollEventListener = (ScrollEventListener) activity;
        } else {
            Log.e(WARN_NO_LISTENER, "The parent activity is not implementing ScrollEventListener");
        }
    }

    @Override
    public void onViewCreated() {
        StaggeredGridLayoutManager layoutManager = new StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.VERTICAL);
        catStreamView.initializeRecyclerView(this, layoutManager);
    }

    @Override
    public void setStreamType(String streamType) {
        Collection<CatPost> catPostCollection =
                catStreamInteractor.getCatPostPageAndType(0, streamType);
        CatPostAdapter adapter = new CatPostAdapter(context, new ArrayList(catPostCollection));
        catStreamView.setAdapter(adapter);
    }

    @Override
    public void onScrollStateChanged(int scrollState) {
        scrollEventListener.onScrollStateChanged(scrollState);
    }
}
