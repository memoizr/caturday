package com.caturday.app.capsules.dashboard.stream.view;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.ksoichiro.android.observablescrollview.ObservableRecyclerView;
import com.github.ksoichiro.android.observablescrollview.ObservableScrollViewCallbacks;
import com.caturday.app.R;
import com.caturday.app.capsules.common.listener.EndlessScrollListener;
import com.caturday.app.capsules.dashboard.adapter.DashboardPageAdapter;
import com.caturday.app.capsules.dashboard.stream.CatStreamModule;
import com.caturday.app.capsules.common.view.mvp.BaseFragment;

import java.util.Arrays;
import java.util.List;

import javax.inject.Inject;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class CatStreamFragment extends BaseFragment implements CatStreamView {

    @Inject CatStreamPresenter catStreamPresenter;
    @InjectView(R.id.cats_stream_RV) ObservableRecyclerView cats_stream_RV;

    private CatPostAdapter catPostAdapter;

    public CatStreamFragment() {
    }

    @Override
    protected List<Object> getModules() {
        return Arrays.<Object>asList(new CatStreamModule(this));
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        catStreamPresenter.onAttach(activity);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_cat_stream, container, false);


        ButterKnife.inject(this, rootView);

        return rootView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        Bundle bundle = getArguments();

        String streamType = bundle.getString(DashboardPageAdapter.STREAM_CATEGORY);
        int streamPosition = bundle.getInt(DashboardPageAdapter.STREAM_POSITION);

        catStreamPresenter.onViewCreated(streamType, streamPosition);

        catStreamPresenter.setAdapterByType(streamType);
    }

    @Override
    public void notifyAdapter() {
        cats_stream_RV.getAdapter().notifyDataSetChanged();
    }

    @Override
    public int getScrollPosition() {
        return cats_stream_RV.getCurrentScrollY();
    }

    @Override
    public void setScrollPosition(int position) {
        ((LinearLayoutManager) cats_stream_RV.getLayoutManager()).scrollToPositionWithOffset(1, 488 - position);
    }

    @Override
    public RecyclerView getRecyclerView() {
        return cats_stream_RV;
    }

    @Override
    public CatPostAdapter getAdapter() {
        return (CatPostAdapter) cats_stream_RV.getAdapter();
    }

    @Override
    public void initializeRecyclerView(ObservableScrollViewCallbacks listener, RecyclerView.LayoutManager layoutManager) {
        cats_stream_RV.setLayoutManager(layoutManager);

        catPostAdapter = new CatPostAdapter(getActivity(), catStreamPresenter);
        cats_stream_RV.setAdapter(catPostAdapter);

        cats_stream_RV.setScrollViewCallbacks(listener);
        cats_stream_RV.setOnScrollListener(new EndlessScrollListener() {
            @Override
            public void onLoadMore(int page, int previousTotalItemCount) {
                catStreamPresenter.loadMore(page, previousTotalItemCount);
            }

            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                catStreamPresenter.onScrollStateChanged(newState);
            }
        });
    }
}
