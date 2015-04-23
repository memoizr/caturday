package com.lovecats.catlover.capsules.favorites.view;

import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.lovecats.catlover.R;
import com.lovecats.catlover.capsules.common.BaseFragment;
import com.lovecats.catlover.capsules.favorites.FavoritesModule;
import com.lovecats.catlover.capsules.favorites.presenter.FavoritesPresenter;
import com.lovecats.catlover.models.catpost.CatPostEntity;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import javax.inject.Inject;

import butterknife.ButterKnife;
import butterknife.InjectView;
import hugo.weaving.DebugLog;

public class FavoritesFragment extends BaseFragment implements FavoritesView {

    @InjectView(R.id.favorites_RV) RecyclerView recyclerView;
    @Inject FavoritesPresenter favoritesPresenter;
    private FavoritesAdapter favoritesAdapter;

    public FavoritesFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_favorites, container, false);

        ButterKnife.inject(this, rootView);
        favoritesPresenter.create(getActivity());

        return rootView;
    }

    @Override
    protected List<Object> getModules() {
        return Arrays.asList(new FavoritesModule(this));
    }

    @Override
    public void initRecyclerView() {

        StaggeredGridLayoutManager layoutManager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
        favoritesAdapter = new FavoritesAdapter(400);
        recyclerView.setAdapter(favoritesAdapter);
    }

    @DebugLog
    @Override
    public void setRecyclerViewAdapter(Collection<CatPostEntity> catPostList) {

        favoritesAdapter.setCatPostEntities(new ArrayList(catPostList));
        favoritesAdapter.notifyDataSetChanged();
    }
}
