package com.caturday.app.models.user.datastore;

import com.caturday.app.models.user.UserEntity;

import rx.Observable;

public interface UserDataStore {
    Observable<UserEntity> updateUser(UserEntity userEntity);

    Observable<UserEntity> getUserForId(String serverId);
}
