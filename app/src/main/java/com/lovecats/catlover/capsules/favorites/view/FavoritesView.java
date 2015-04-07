package com.lovecats.catlover.capsules.favorites.view;

import com.lovecats.catlover.models.catpost.CatPostEntity;

import java.util.Collection;

public interface FavoritesView {
    void initRecyclerView();

    void setRecyclerViewAdapter(Collection<CatPostEntity> catPostList);
}
