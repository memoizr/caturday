package com.lovecats.catlover.capsules.favorites.interactor;

import com.lovecats.catlover.models.catpost.CatPostEntity;

import java.util.Collection;

import rx.Observable;

public interface FavoritesInteractor {

    Collection<CatPostEntity> getFavoriteCatPosts();
}
