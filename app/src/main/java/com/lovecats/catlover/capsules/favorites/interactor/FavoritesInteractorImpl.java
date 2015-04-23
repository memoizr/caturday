package com.lovecats.catlover.capsules.favorites.interactor;

import com.lovecats.catlover.models.catpost.CatPostEntity;
import com.lovecats.catlover.models.catpost.repository.CatPostRepository;
import com.lovecats.catlover.models.user.repository.UserRepository;

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
