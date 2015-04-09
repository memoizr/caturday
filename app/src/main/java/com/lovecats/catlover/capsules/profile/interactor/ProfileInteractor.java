package com.lovecats.catlover.capsules.profile.interactor;

import com.lovecats.catlover.models.user.UserEntity;

/**
 * Created by user on 28/03/15.
 */
public interface ProfileInteractor {
    void logout();

    boolean userLoggedIn();

    UserEntity getUser();
}
