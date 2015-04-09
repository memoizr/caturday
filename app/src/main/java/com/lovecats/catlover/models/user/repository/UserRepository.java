package com.lovecats.catlover.models.user.repository;

import com.lovecats.catlover.models.user.UserEntity;

import java.util.HashSet;

public interface UserRepository {

    UserEntity getCurrentUser();

    void addFavoritePost(String serverId);

    void removeFavoritePost(String serverId);

    HashSet<String> getAllFavoritePost();

    boolean userLoggedIn();

    void logout();

    void updateUserName(String userName);

    void updateDescription(String userDescription);

    void updateEmail(String userEmail);
}
