package com.caturday.app.capsules.favorites.view;

import android.os.Bundle;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.caturday.app.R;
import com.caturday.app.capsules.common.view.mvp.BaseFragment;
import com.caturday.app.capsules.favorites.FavoritesModule;
import com.caturday.app.capsules.favorites.presenter.FavoritesPresenter;
import com.caturday.app.models.catpost.CatPostEntity;
import com.github.ksoichiro.android.observablescrollview.ObservableRecyclerView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import javax.inject.Inject;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class FavoritesFragment extends BaseFragment implements FavoritesView {

    @InjectView(R.id.favorites_RV) ObservableRecyclerView recyclerView;
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
        int headerHeight = getResources().getDimensionPixelSize(R.dimen.dashboard_image_height);

        StaggeredGridLayoutManager layoutManager = new StaggeredGridLayoutManager(
                2, StaggeredGridLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);

        recyclerView.setScrollViewCallbacks(favoritesPresenter);
        favoritesAdapter = new FavoritesAdapter(headerHeight);
        recyclerView.setAdapter(favoritesAdapter);
    }

    @Override
    public void setRecyclerViewAdapter(Collection<CatPostEntity> catPostList) {
        ((FavoritesAdapter)recyclerView.getAdapter()).setCatPostEntities(new ArrayList(catPostList));
    }
}
