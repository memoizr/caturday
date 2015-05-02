package com.caturday.app.capsules.main.interactor;

import com.caturday.app.models.catpost.CatPostEntity;

import java.util.Collection;

/**
 * Created by Cat#2 on 09/04/15.
 */
public interface MainInteractor {

    boolean userLoggedIn();

    void performLogout();

    Collection<CatPostEntity> getRandomCatPosts(int howMany);
}
