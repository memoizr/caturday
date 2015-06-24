package com.caturday.app.capsules.favorites.interactor;

import com.caturday.app.models.catpost.CatPostEntity;
import com.caturday.app.models.catpost.repository.CatPostRepository;
import com.caturday.app.models.user.repository.UserRepository;

import java.util.Collection;
import java.util.HashSet;

import rx.Observable;

public class FavoritesInteractorImpl implements FavoritesInteractor {

    private final UserRepository userRepository;
    private final CatPostRepository catPostRepository;


    public FavoritesInteractorImpl(UserRepository userRepository, CatPostRepository catPostRepository) {
        this.userRepository = userRepository;
        this.catPostRepository = catPostRepository;
    }

    @Override
    public Observable<Collection<CatPostEntity>> getFavoriteCatPosts() {

        Observable<HashSet<String>> favoriteIds = userRepository.getAllFavoritePost();
        return favoriteIds.flatMap(catPostRepository::getCatPostsForIds);
    }
}
