package com.lovecats.catlover.models.user;

import com.lovecats.catlover.util.data.GsonMapper;

import greendao.User;

public class UserMapper {

    public static User fromEntity(UserEntity userEntity) {
        User user = new User();
        user.setUsername(userEntity.getUsername());
        user.setServerId(userEntity.getServerId());
//        user.setAuthToken(userEntity.getAuthToken());
        user.setDescription(userEntity.getDescription());
        user.setImageUrl(userEntity.getImageUrl());
        user.setCoverImageUrl(userEntity.getCoverImageUrl());
        user.setEmail(userEntity.getEmail());
        user.setLastName(userEntity.getLastName());
        user.setInfo(userEntity.getInfo());
        user.setFavorites(userEntity.getFavorites().toString());
        user.setFirstName(userEntity.getFirstName());
        return user;
    }

    public static UserEntity fromUser(User user) {
        UserEntity userEntity = new UserEntity();
        userEntity.setUsername(user.getUsername());

        userEntity.setServerId(user.getServerId());
//        userEntity.setAuthToken(user.getAuthToken());
        userEntity.setDescription(user.getDescription());
        userEntity.setCoverImageUrl(user.getCoverImageUrl());
        userEntity.setImageUrl(user.getImageUrl());
        userEntity.setEmail(user.getEmail());
        userEntity.setLastName(user.getLastName());
        userEntity.setInfo(user.getInfo());
        userEntity.setFavorites(GsonMapper.toJsonArray(user.getFavorites()));
        userEntity.setFirstName(user.getFirstName());
        return userEntity;
    }
}
