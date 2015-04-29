package com.lovecats.catlover.models.user.datastore;

import com.lovecats.catlover.models.user.UserEntity;
import com.lovecats.catlover.models.user.api.UserApi;

import rx.Observable;

public class UserCloudDataStore implements UserDataStore {

    private final UserApi userApi;

    public UserCloudDataStore(UserApi userApi) {
        this.userApi = userApi;

    }

    @Override
    public Observable<UserEntity> updateUser(UserEntity userEntity) {
        return userApi.updateUser(userEntity);
    }

    @Override
    public Observable<UserEntity> getUserForId(String serverId) {
        return userApi.getUserForId(serverId);
    }

    public Observable<UserEntity> unfollowUser(String serverId) {
        return userApi.unfollowUser(serverId);
    }

    public Observable<UserEntity> followUser(String serverId) {
        return userApi.followUser(serverId);
    }
}
