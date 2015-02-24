package com.lovecats.catlover;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.transition.Fade;
import android.transition.Transition;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.lovecats.catlover.adapters.NewCatsAdapter;
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

public class NewCatsFragment extends Fragment {
    @InjectView(R.id.new_cats_RV) RecyclerView new_cats_RV;
    public List<String> catUrlList;

    public NewCatsFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_new_cats, container, false);
        ButterKnife.inject(this, rootView);
        return rootView;
    }
    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        StaggeredGridLayoutManager staggeredGrid = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.GAP_HANDLING_NONE);
        staggeredGrid.setOrientation(StaggeredGridLayoutManager.VERTICAL);

        new_cats_RV.setLayoutManager(staggeredGrid);

        if (CatModel.getCount(getActivity()) == 0) {
            getCatImageUrls(getActivity());
        }

        List<CatImage> catImages = CatModel.getAllCatImages(getActivity());
        new_cats_RV.setAdapter(new NewCatsAdapter(getActivity(), catImages));
    }

    private void notifyAdapter(List<String> urls){
        fetchImages(urls);
        new_cats_RV.getAdapter().notifyDataSetChanged();

    }

    private void fetchImages(List<String> urls) {
        for (String image : urls) {
            CatImage catImage = new CatImage();
            catImage.setUrl(image);
            CatModel.insertOrUpdate(getActivity(), catImage);
        }
    }

    private List<String> getCatImageUrls(Context context) {
        Context that = context;
        final List<String> urls = new ArrayList<>();

        Thread thread = new Thread() {
            @Override
            public void run() {
                try {
                    String response;
                    String request = "http://thecatapi.com/api/images/get?format=xml&type=jpg&results_per_page=60";
                    response = XMLParser.getIt(request);
                    Document doc = XMLParser.getDomElement(response);
                    NodeList nl = doc.getElementsByTagName("url");

                    for (int i = 0; i < nl.getLength(); i++) {
                        Element e = (Element) nl.item(i);
                        urls.add(e.getTextContent());
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            notifyAdapter(urls);
                        }
                    });
                }

            }
        };
        thread.start();
        return urls;
    }
}
