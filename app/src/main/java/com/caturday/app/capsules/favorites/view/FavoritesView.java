package com.caturday.app.capsules.favorites.view;

import com.caturday.app.models.catpost.CatPostEntity;

import java.util.Collection;

public interface FavoritesView {
    void initRecyclerView();

    void setRecyclerViewAdapter(Collection<CatPostEntity> catPostList);
}
