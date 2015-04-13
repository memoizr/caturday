package com.lovecats.catlover.capsules.dashboard.stream.presenter;

import android.app.Activity;
import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;

import com.github.ksoichiro.android.observablescrollview.ScrollState;
import com.lovecats.catlover.capsules.common.listener.ScrollEventListener;
import com.lovecats.catlover.models.catpost.CatPostEntity;
import com.lovecats.catlover.capsules.dashboard.stream.view.adapter.CatPostAdapter;
import com.lovecats.catlover.capsules.dashboard.stream.interactor.CatStreamInteractor;
import com.lovecats.catlover.capsules.dashboard.stream.view.CatStreamView;

import java.util.ArrayList;
import java.util.Collection;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class CatStreamPresenterImpl extends CatStreamPresenter {

    private final CatStreamView catStreamView;
    private final CatStreamInteractor catStreamInteractor;
    private ScrollEventListener scrollEventListener;
    private static final String WARN_NO_LISTENER = "No Listeners attached";
    private Context context;
    private String streamType;

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
        LinearLayoutManager layoutManager = new LinearLayoutManager(context);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        catStreamView.initializeRecyclerView(this, layoutManager);
    }

    @Override
    public void onScrollStateChanged(int scrollState) {
        scrollEventListener.onScrollStateChanged(scrollState);
    }

    @Override
    public void setAdapterByType(String streamType) {
        this.streamType = streamType;
        catStreamInteractor.getCatPostPageAndType(0,
                streamType,
                false,
                new Callback<Collection<CatPostEntity>>() {
            @Override
            public void success(Collection<CatPostEntity> catPostCollection, Response response) {
                CatPostAdapter adapter = new CatPostAdapter(context, new ArrayList(catPostCollection));
                catStreamView.setAdapter(adapter);
            }

            @Override
            public void failure(RetrofitError error) {

            }
        });
    }

    @Override
    public void loadMore(int page, int totalItems) {
        // TODO switch to RX
        catStreamInteractor.getCatPostPageAndType(page,
                streamType,
                false,
                new Callback<Collection<CatPostEntity>>() {
            @Override
            public void success(Collection<CatPostEntity> catPostCollection, Response response) {
                CatPostAdapter adapter = (CatPostAdapter) catStreamView.getAdapter();
                adapter.addItems(catPostCollection);
            }

            @Override
            public void failure(RetrofitError error) {
                System.out.println("failure");
            }
        });
    }
}
