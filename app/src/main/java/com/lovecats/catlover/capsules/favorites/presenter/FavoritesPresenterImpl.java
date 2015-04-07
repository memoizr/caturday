package com.lovecats.catlover.capsules.favorites.presenter;

import android.content.Context;

import com.lovecats.catlover.capsules.favorites.interactor.FavoritesInteractor;
import com.lovecats.catlover.capsules.favorites.view.FavoritesView;
import com.lovecats.catlover.models.catpost.CatPostEntity;

import java.util.Collection;

public class FavoritesPresenterImpl implements FavoritesPresenter {
    private final FavoritesView favoritesView;
    private final FavoritesInteractor favoritesInteractor;
    private Context context;

    public FavoritesPresenterImpl(FavoritesView favoritesView,
                                  FavoritesInteractor favoritesInteractor) {
        this.favoritesView = favoritesView;
        this.favoritesInteractor = favoritesInteractor;
    }

    @Override
    public void create(Context context) {
        this.context = context;
        favoritesView.initRecyclerView();
        Collection<CatPostEntity> catPostEntityCollection = favoritesInteractor.getFavoriteCatPosts();

        favoritesView.setRecyclerViewAdapter(catPostEntityCollection);
    }
}
