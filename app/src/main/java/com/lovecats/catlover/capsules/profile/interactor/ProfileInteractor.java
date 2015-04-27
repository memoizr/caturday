package com.lovecats.catlover.capsules.profile.interactor;

import com.lovecats.catlover.models.user.UserEntity;

import rx.Observable;

public interface ProfileInteractor {

    void logout();

    boolean userLoggedIn();

    void updateUserName(String userName);

    Observable<UserEntity> getUserForId(String serverId);
}
