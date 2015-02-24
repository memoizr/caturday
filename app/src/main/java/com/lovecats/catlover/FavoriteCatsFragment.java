package com.lovecats.catlover;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.ksoichiro.android.observablescrollview.ObservableRecyclerView;
import com.lovecats.catlover.adapters.NewCatsAdapter;
import com.lovecats.catlover.data.CatModel;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import greendao.CatImage;


public class FavoriteCatsFragment extends Fragment {
    @InjectView(R.id.fav_cats_RV) ObservableRecyclerView fav_cats_RV;
    private static List<CatImage> catImages;
    private NewCatsAdapter newCatsAdapter;


    public FavoriteCatsFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_favorite_cats, container, false);

        ButterKnife.inject(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        StaggeredGridLayoutManager staggeredGrid = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.GAP_HANDLING_MOVE_ITEMS_BETWEEN_SPANS);
        staggeredGrid.setOrientation(StaggeredGridLayoutManager.VERTICAL);

        fav_cats_RV.setLayoutManager(staggeredGrid);

        catImages = CatModel.getAllFavoriteCatImages(getActivity());
        newCatsAdapter = new NewCatsAdapter(getActivity(), catImages);
        fav_cats_RV.setAdapter(newCatsAdapter);
    }

    @Override
    public void onResume(){
        catImages = CatModel.getAllFavoriteCatImages(getActivity());
        newCatsAdapter.mCatImages = catImages;
        newCatsAdapter.notifyDataSetChanged();
        super.onResume();
    }

}
