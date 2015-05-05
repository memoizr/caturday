package com.caturday.app.models.user.repository;

import com.caturday.app.models.user.UserEntity;
import com.caturday.app.models.user.datastore.UserCloudDataStore;
import com.caturday.app.models.user.db.UserORM;

import java.util.HashSet;

import rx.Observable;

public class UserRepositoryImpl implements UserRepository {

    private final UserORM userORM;
    private final UserCloudDataStore userCloudDataStore;

    public UserRepositoryImpl(UserORM userORM, UserCloudDataStore userCloudDataStore) {
        this.userORM = userORM;
        this.userCloudDataStore = userCloudDataStore;
    }

    @Override
    public UserEntity getCurrentUser() {
        return userORM.getLoggedInUser();
    }

    @Override
    public void addFavoritePost(String serverId) {
        userORM.addFavorite(serverId);
    }

    @Override
    public void removeFavoritePost(String serverId) {
        userORM.removeFavorite(serverId);
    }

    @Override
    public Observable<HashSet<String>> getAllFavoritePost() {
        return Observable.just(userORM.getFavoriteCatPosts());
    }

    @Override
    public boolean userLoggedIn() {
        return userORM.userLoggedIn();
    }

    @Override
    public void logout() {
        userORM.performLogout();
    }

    @Override
    public Observable<UserEntity> updateUser(UserEntity userEntity) {
        return userCloudDataStore.updateUser(userEntity);
    }

    @Override
    public Observable<UserEntity> getUserForId(String id) {
        return userCloudDataStore.getUserForId(id);
    }

    @Override
    public Observable<UserEntity> followUser(String serverId) {
        return userCloudDataStore.followUser(serverId);
    }

    @Override
    public Observable<UserEntity> unfollowUser(String serverId) {
        return userCloudDataStore.unfollowUser(serverId);
    }


}
