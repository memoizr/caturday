package com.lovecats.catlover.models.user.repository;


import com.lovecats.catlover.models.user.UserEntity;
import com.lovecats.catlover.models.user.db.UserORM;

import java.util.ArrayList;
import java.util.HashSet;

public class UserRepositoryImpl implements UserRepository {

    private final UserORM userORM;

    public UserRepositoryImpl(UserORM userORM) {
        this.userORM = userORM;
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
    public HashSet<String> getAllFavoritePost() {
        return userORM.getFavoriteCatPosts();
    }
}
