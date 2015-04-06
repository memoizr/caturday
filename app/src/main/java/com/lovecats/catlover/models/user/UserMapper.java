package com.lovecats.catlover.models.user;

import greendao.User;

/**
 * Created by Cat#2 on 05/04/15.
 */
public class UserMapper {

    public static User fromEntity(UserEntity userEntity) {
        User user = new User();
        user.setUsername(userEntity.getUsername());
        user.setServerId(userEntity.getServerId());
        user.setAuthToken(userEntity.getAuthToken());
        user.setDescription(userEntity.getDescription());
        user.setImage_url(userEntity.getImageUrl());
        user.setEmail(userEntity.getEmail());
        user.setLastName(userEntity.getLastName());
        user.setInfo(userEntity.getInfo());
        user.setFavorites(userEntity.getFavorites());
        user.setFirstName(userEntity.getFirstName());
        return user;
    }

    public static UserEntity fromUser(User user) {
        UserEntity userEntity = new UserEntity();
        userEntity.setUsername(user.getUsername());
        userEntity.setServerId(user.getServerId());
        userEntity.setAuthToken(user.getAuthToken());
        userEntity.setDescription(user.getDescription());
        userEntity.setImageUrl(user.getImage_url());
        userEntity.setEmail(user.getEmail());
        userEntity.setLastName(user.getLastName());
        userEntity.setInfo(user.getInfo());
        userEntity.setFavorites(user.getFavorites());
        userEntity.setFirstName(user.getFirstName());
        return userEntity;
    }
}
