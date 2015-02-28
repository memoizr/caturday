package com.lovecats.catlover;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.ksoichiro.android.observablescrollview.ObservableRecyclerView;
import com.github.ksoichiro.android.observablescrollview.ObservableScrollViewCallbacks;
import com.github.ksoichiro.android.observablescrollview.ScrollState;
import com.lovecats.catlover.adapters.CatsAdapter;
import com.lovecats.catlover.data.CatFetcher;
import com.lovecats.catlover.data.CatModel;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import greendao.CatImage;

/**
 * Created by user on 26/02/15.
 */
public class CatStreamFragment extends Fragment implements ObservableScrollViewCallbacks,
        CatFetcher.FetcherCallback {
    @InjectView(R.id.cats_stream_RV) ObservableRecyclerView cats_stream_RV;

    public static final int NEW_STREAM_TYPE = 0;
    public static final int FAVORITES_STREAM_TYPE = 1;

    private Callback catScrollCallback;
    private int streamType;
    private List<CatImage> catImages;
    private CatsAdapter catsAdapter;
    private CatFetcher catFetcher;

    public CatStreamFragment() {
    }

    @Override
    public void onFetchComplete(List<CatImage> catImages) {
        catsAdapter.mCatImages = catImages;
        notifyAdapter();

    }

    public interface Callback {
        void onScroll(Fragment fragment, int scrollY, boolean firstScroll, boolean dragging);

        void onUpOrCancelMotionEvent(Fragment fragment, ScrollState scrollState);

        public void onScrollStateChanged(Fragment fragment, int scrollState);
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        if (activity instanceof Callback) {
            catScrollCallback = (Callback) activity;
        }
    }

    @Override
    public void onScrollChanged(int scrollY, boolean firstScroll, boolean dragging) {
        catScrollCallback.onScroll(this, scrollY, firstScroll, dragging);
    }

    @Override
    public void onDownMotionEvent() {
    }

    @Override
    public void onUpOrCancelMotionEvent(ScrollState scrollState) {
        catScrollCallback.onUpOrCancelMotionEvent(this, scrollState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_cat_stream, container, false);

        Bundle bundle = getArguments();
        streamType = bundle.getInt("streamType");

        ButterKnife.inject(this, rootView);
        return rootView;
    }

    public void fetchCats() {

        catFetcher = new CatFetcher(getActivity(), this);
        catFetcher.getCatImageUrls();
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        final Fragment that = this;

        StaggeredGridLayoutManager staggeredGrid = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.GAP_HANDLING_NONE);
        staggeredGrid.setOrientation(StaggeredGridLayoutManager.VERTICAL);

        cats_stream_RV.setLayoutManager(staggeredGrid);


        if (CatModel.getCount(getActivity()) == 0) {
            fetchCats();
        }

        cats_stream_RV.setScrollViewCallbacks(this);
        cats_stream_RV.setOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                catScrollCallback.onScrollStateChanged(that, newState);
            }
        });

        if (streamType == NEW_STREAM_TYPE) {
            catImages = CatModel.getLastFourtyImages(getActivity());
        } else {
            catImages = CatModel.getAllFavoriteCatImages(getActivity());
        }
        catsAdapter = new CatsAdapter(getActivity(), catImages);
        cats_stream_RV.setAdapter(catsAdapter);
    }

    private void notifyAdapter(){
        cats_stream_RV.getAdapter().notifyDataSetChanged();
    }

    public int getScrollPosition() {

        return cats_stream_RV.getCurrentScrollY();
    }

    public void setScrollPosition(int position) {
        ((StaggeredGridLayoutManager)cats_stream_RV.getLayoutManager()).scrollToPositionWithOffset(1,440 - position);
    }

    @Override
    public void onResume(){
        if (streamType == FAVORITES_STREAM_TYPE) {
            catImages = CatModel.getAllFavoriteCatImages(getActivity());
            catsAdapter.mCatImages = catImages;
            catsAdapter.notifyDataSetChanged();
        }
        super.onResume();
    }
}
