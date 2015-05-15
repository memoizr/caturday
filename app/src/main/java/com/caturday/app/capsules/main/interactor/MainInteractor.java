package com.caturday.app.capsules.main.interactor;

import com.caturday.app.models.catpost.CatPostEntity;
import com.caturday.app.models.user.UserEntity;

import java.util.Collection;

public interface MainInteractor {

    boolean userLoggedIn();

    void performLogout();

    UserEntity getCurrentUser();

    Collection<CatPostEntity> getRandomCatPosts(int howMany);
}
