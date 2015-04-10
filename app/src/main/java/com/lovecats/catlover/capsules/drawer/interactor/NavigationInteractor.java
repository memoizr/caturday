package com.lovecats.catlover.capsules.drawer.interactor;

import com.lovecats.catlover.models.user.UserEntity;

/**
 * Created by user on 28/03/15.
 */
public interface NavigationInteractor {
    String[] provideNavigationItems();

    boolean isUserLoggedIn();

    UserEntity getLoggedInUser();
}
