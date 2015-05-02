package com.caturday.app.models.user.datastore;

import com.caturday.app.models.user.UserEntity;

import rx.Observable;

public class UserLocalDataStore implements UserDataStore {
    @Override
    public Observable<UserEntity> updateUser(UserEntity userEntity) {
        return null;
    }

    @Override
    public Observable<UserEntity> getUserForId(String serverId) {
        return null;
    }
}
