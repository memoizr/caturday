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
import com.lovecats.catlover.adapters.CatPostAdapter;
import com.lovecats.catlover.data.CatPostFetcher;
import com.lovecats.catlover.data.CatPostModel;
import com.lovecats.catlover.util.EventBus;
import com.squareup.otto.Subscribe;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import greendao.CatPost;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class CatStreamFragment extends Fragment implements ObservableScrollViewCallbacks
        {
    @InjectView(R.id.cats_stream_RV) ObservableRecyclerView cats_stream_RV;

    public static final int NEW_STREAM_TYPE = 0;
    public static final int FAVORITES_STREAM_TYPE = 1;

    private ScrollCallback catScrollCallback;
    private int streamType;
    private List<CatPost> mCatPosts;
    private CatPostAdapter catPostAdapter;
    private CatPostFetcher catPostFetcher;
    private StaggeredGridLayoutManager staggeredGrid;

    public CatStreamFragment() {
    }

//    @Override
    public void onFetchComplete(List<CatPostModel> catPostModels) {
        catPostAdapter.mCatPosts = CatPostModel.getAllCatPosts();
        notifyAdapter();

    }

    public interface ScrollCallback {
        void onScroll(Fragment fragment, int scrollY, boolean firstScroll, boolean dragging);

        void onUpOrCancelMotionEvent(Fragment fragment, ScrollState scrollState);

        public void onScrollStateChanged(Fragment fragment, int scrollState);
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        if (activity instanceof ScrollCallback) {
            catScrollCallback = (ScrollCallback) activity;
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

    private void fetchPosts(){
        CatPostFetcher.getSession().fetchCatPosts(new CatPostFetcher.CatPostFetcherCallbacks() {
            @Override
            public void onSuccess(List<CatPostModel> catPostModels) {
                onFetchComplete(catPostModels);
            }
        });
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        EventBus.getInstance().register(this);

        final Fragment that = this;

        if (streamType == 0) {
            staggeredGrid = new StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.GAP_HANDLING_NONE);
        } else {
            staggeredGrid = new StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.GAP_HANDLING_NONE);
        }
        staggeredGrid.setOrientation(StaggeredGridLayoutManager.VERTICAL);

        cats_stream_RV.setLayoutManager(staggeredGrid);

        System.out.println(CatPostModel.getCount());


        cats_stream_RV.setScrollViewCallbacks(this);
        cats_stream_RV.setOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                catScrollCallback.onScrollStateChanged(that, newState);
            }
        });

        switch (streamType) {
            case 0:
                mCatPosts = CatPostModel.getPostsForCategory("space");
                break;
            case 1:
                mCatPosts = CatPostModel.getPostsForCategory("boxes");
                break;
            case 2:
                mCatPosts = CatPostModel.getPostsForCategory("caturday");
                break;
            case 3:
                mCatPosts = CatPostModel.getPostsForCategory("hats");
                break;
            case 4:
                mCatPosts = CatPostModel.getPostsForCategory("sunglasses");
                break;
            default:
                mCatPosts = CatPostModel.getPostsForCategory("dream");
        }

        catPostAdapter = new CatPostAdapter(getActivity(), mCatPosts);
        cats_stream_RV.setAdapter(catPostAdapter);

        if (CatPostModel.getCount() == 0 && streamType == NEW_STREAM_TYPE) {
            fetchPosts();
        }
    }

    private void notifyAdapter(){
        cats_stream_RV.getAdapter().notifyDataSetChanged();
    }

    public int getScrollPosition() {

        return cats_stream_RV.getCurrentScrollY();
    }

    public void setScrollPosition(int position) {
        ((StaggeredGridLayoutManager)cats_stream_RV.getLayoutManager()).scrollToPositionWithOffset(1, 488 - position);
    }

//    @Override
//    public void onResume(){
//        if (streamType == FAVORITES_STREAM_TYPE) {
//            catImages = CatModel.getAllFavoriteCatImages(getActivity());
//            catsAdapter.mCatImages = catImages;
//            catsAdapter.notifyDataSetChanged();
//        }
//        super.onResume();
//    }
}
