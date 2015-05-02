package com.caturday.app.capsules.favorites.interactor;

import com.caturday.app.models.catpost.CatPostEntity;

import java.util.Collection;

import rx.Observable;

public interface FavoritesInteractor {

    Observable<Collection<CatPostEntity>> getFavoriteCatPosts();
}
