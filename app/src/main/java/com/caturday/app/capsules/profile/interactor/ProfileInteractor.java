package com.caturday.app.capsules.profile.interactor;

import com.caturday.app.models.user.UserEntity;

import rx.Observable;

public interface ProfileInteractor {

    void logout();

    boolean userLoggedIn();

    UserEntity getCurrentUser();

    void updateUserName(String userName);

    Observable<UserEntity> getUserForId(String serverId);

    Observable<UserEntity> followUser(String serverId);

    Observable<UserEntity> unfollowUser(String serverId);
}
