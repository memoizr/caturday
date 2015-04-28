package com.lovecats.catlover.models.user.datastore;

import com.lovecats.catlover.models.user.UserEntity;

import rx.Observable;

public interface UserDataStore {
    Observable<UserEntity> updateUser(UserEntity userEntity);

    Observable<UserEntity> getUserForId(String serverId);
}
