package com.lovecats.catlover.capsules.dashboard.stream.view;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.ksoichiro.android.observablescrollview.ObservableRecyclerView;
import com.github.ksoichiro.android.observablescrollview.ObservableScrollViewCallbacks;
import com.lovecats.catlover.R;
import com.lovecats.catlover.capsules.common.listener.EndlessScrollListener;
import com.lovecats.catlover.models.catpost.CatPostEntity;
import com.lovecats.catlover.capsules.dashboard.stream.view.adapter.CatPostAdapter;
import com.lovecats.catlover.capsules.dashboard.adapter.DashboardPageAdapter;
import com.lovecats.catlover.capsules.dashboard.stream.CatStreamModule;
import com.lovecats.catlover.capsules.dashboard.stream.presenter.CatStreamPresenter;
import com.lovecats.catlover.capsules.common.BaseFragment;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.inject.Inject;

import butterknife.ButterKnife;
import butterknife.InjectView;
import hugo.weaving.DebugLog;

public class CatStreamFragment extends BaseFragment implements CatStreamView {

    @Inject CatStreamPresenter catStreamPresenter;
    @InjectView(R.id.cats_stream_RV) ObservableRecyclerView cats_stream_RV;

    private List<CatPostEntity> mCatPosts = new ArrayList<>();
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

        catStreamPresenter.onViewCreated();

        catPostAdapter = new CatPostAdapter(getActivity(), mCatPosts);

        Bundle bundle = getArguments();

        String streamType = bundle.getString(DashboardPageAdapter.STREAM_CATEGORY);

        catStreamPresenter.setAdapterByType(streamType);
    }

    @Override
    public void notifyAdapter() {
        cats_stream_RV.getAdapter().notifyDataSetChanged();
    }

    public int getScrollPosition() {
        return cats_stream_RV.getCurrentScrollY();
    }

    public void setScrollPosition(int position) {
        ((StaggeredGridLayoutManager) cats_stream_RV.getLayoutManager()).scrollToPositionWithOffset(1, 488 - position);
    }

    @Override
    public void setAdapter(final RecyclerView.Adapter adapter) {
        cats_stream_RV.setAdapter(adapter);
    }

    @Override
    public RecyclerView.Adapter getAdapter() {
        return cats_stream_RV.getAdapter();
    }

    @Override
    public void initializeRecyclerView(ObservableScrollViewCallbacks listener, RecyclerView.LayoutManager layoutManager) {
        cats_stream_RV.setLayoutManager(layoutManager);

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
