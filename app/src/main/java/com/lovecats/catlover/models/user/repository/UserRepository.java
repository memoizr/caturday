package com.lovecats.catlover.models.user.repository;

import com.lovecats.catlover.models.user.UserEntity;

import java.util.HashSet;

import rx.Observable;

public interface UserRepository {

    UserEntity getCurrentUser();

    void addFavoritePost(String serverId);

    void removeFavoritePost(String serverId);

    Observable<HashSet<String>> getAllFavoritePost();

    Observable<UserEntity> login(String email, String password);

    Observable<UserEntity> signup(String username, String email, String password);

    Observable<UserEntity> saveUser(UserEntity userEntity);

    boolean userLoggedIn();

    void logout();

    Observable<UserEntity> updateUser(UserEntity userEntity);

    Observable<UserEntity> getUserForId(String id);

    Observable<UserEntity> followUser(String serverId);

    Observable<UserEntity> unfollowUser(String serverId);
}
