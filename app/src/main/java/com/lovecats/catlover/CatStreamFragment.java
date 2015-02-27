package com.lovecats.catlover;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
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
import com.lovecats.catlover.helpers.XMLParser;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import java.util.ArrayList;
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

    public List<String> catUrlList;
    private Callback catScrollCallback;
    private static final String request = "http://thecatapi.com/api/images/get?format=xml&type=jpg&results_per_page=60";
    private int streamType;
    private List<CatImage> catImages;
    private CatsAdapter catsAdapter;
    private CatFetcher catFetcher;

    public CatStreamFragment() {
    }

    @Override
    public void onFetchComplete(List<CatImage> catImages) {
        catsAdapter.mCatImages = catImages;
        System.out.println("fetch callback called from fragment");
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
        System.out.println("fetch cats from fragment");
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

        System.out.println("adapter notified");
        cats_stream_RV.getAdapter().notifyDataSetChanged();
    }

//    private void fetchImages(List<String> urls) {
//        for (String image : urls) {
//            CatImage catImage = new CatImage();
//            catImage.setUrl(image);
//            CatModel.insertOrUpdate(getActivity(), catImage);
//        }
//    }
//
//    public List<String> getCatImageUrls(Context context) {
//        final List<String> urls = new ArrayList<>();
//
//        Thread thread = new Thread() {
//            @Override
//            public void run() {
//                try {
//                    String response;
//                    response = XMLParser.getIt(request);
//                    Document doc = XMLParser.getDomElement(response);
//                    NodeList nl = doc.getElementsByTagName("url");
//
//                    for (int i = 0; i < nl.getLength(); i++) {
//                        Element e = (Element) nl.item(i);
//                        urls.add(e.getTextContent());
//                    }
//
//                } catch (Exception e) {
//                    e.printStackTrace();
//                } finally {
//                    getActivity().runOnUiThread(new Runnable() {
//                        @Override
//                        public void run() {
//                            notifyAdapter(urls);
//                        }
//                    });
//                }
//            }
//        };
//        thread.start();
//        return urls;
//    }

    public void setScrollPosition(int position) {
        if (cats_stream_RV != null) {
            cats_stream_RV.scrollVerticallyTo(position);
        }
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
