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
import com.caturday.app.capsules.dashboard.stream.CatStreamModule;
import com.caturday.app.capsules.common.view.mvp.BaseFragment;

import java.util.Arrays;
import java.util.List;

import javax.inject.Inject;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class CatStreamFragment extends BaseFragment implements CatStreamView {

    public static final String STREAM_CATEGORY = "streamCategory";
    public static final String STREAM_POSITION = "streamPosition";
    public static final String STREAM_USER_ID = "streamUserId";

    @Inject CatStreamPresenter catStreamPresenter;
    @InjectView(R.id.cats_stream_RV) ObservableRecyclerView cats_stream_RV;
    @InjectView(R.id.logged_out_empty_V) View emptyV;

    private CatPostAdapter catPostAdapter;

    public CatStreamFragment() {
    }

    @Override
    protected List<Object> getModules() {
        return Arrays.asList(new CatStreamModule(this));
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

        String streamType = bundle.getString(STREAM_CATEGORY);
        String userId = bundle.getString(STREAM_USER_ID);
        int streamPosition = bundle.getInt(STREAM_POSITION);

        catStreamPresenter.onViewCreated(streamType, userId, streamPosition);

        catStreamPresenter.setAdapter();
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
        System.out.println("scroll to: " + position);
        ((LinearLayoutManager) cats_stream_RV.getLayoutManager()).scrollToPositionWithOffset(1,
                getResources().getDimensionPixelSize(R.dimen.scroll_tab_padding) - position);

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
    public void showEmptyView(boolean showIt) {
        if (showIt) {
            emptyV.setVisibility(View.VISIBLE);
            cats_stream_RV.setVisibility(View.GONE);
        } else {
            emptyV.setVisibility(View.GONE);
            cats_stream_RV.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onPageSelected() {
        cats_stream_RV.scrollBy(0,1);
        cats_stream_RV.scrollBy(0,-1);
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

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        catStreamPresenter.onDestroyView();
    }
}
