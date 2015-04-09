package com.lovecats.catlover.capsules.main.interactor;

import com.lovecats.catlover.models.catpost.CatPostEntity;

import java.util.Collection;

/**
 * Created by Cat#2 on 09/04/15.
 */
public interface MainInteractor {

    boolean userLoggedIn();

    Collection<CatPostEntity> getRandomCatPosts(int howMany);
}
