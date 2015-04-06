package com.lovecats.catlover.models.user.repository;

import com.lovecats.catlover.models.user.UserEntity;

import java.util.ArrayList;

public interface UserRepository {

    UserEntity getCurrentUser();

    void addFavoritePost(String serverId);

    void removeFavoritePost(String serverId);

    ArrayList<String> getAllFavoritePost();
}
