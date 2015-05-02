package com.caturday.app.capsules.drawer.interactor;

import com.caturday.app.models.user.UserEntity;

/**
 * Created by user on 28/03/15.
 */
public interface NavigationInteractor {
    String[] provideNavigationItems();

    boolean isUserLoggedIn();

    UserEntity getLoggedInUser();
}
