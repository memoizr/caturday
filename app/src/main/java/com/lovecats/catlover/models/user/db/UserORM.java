package com.lovecats.catlover.models.user.db;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.reflect.TypeToken;
import com.lovecats.catlover.models.user.UserEntity;
import com.lovecats.catlover.models.user.UserMapper;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

import greendao.DaoSession;
import greendao.User;
import greendao.UserDao;

public class UserORM {

    private DaoSession daoSession;

    private static Gson gson = new Gson();

    public UserORM(DaoSession daoSession) {
        this.daoSession = daoSession;
    }

    public User logInUser(UserEntity userEntity) {
        flushUsers();
        User user = UserMapper.fromEntity(userEntity);
        user.setLoggedIn(true);
        long id = getUserDao().insertOrReplace(user);
        return getUserDao().loadByRowId(id);
    }

    private UserDao getUserDao() {
        return daoSession.getUserDao();
    }

    public void flushUsers() {
        getUserDao().deleteAll();
    }

    public boolean userLoggedIn() {
        return getUserDao().count() > 0;
    }

    public ArrayList<String> getFavoriteCatPosts(){
        String favorites;
        favorites = currentUser().getFavorites();
        if (favorites == null) {
            favorites = "[]";
        }
//        Collection<String> idList = gson.fromJson(favorites, collectionType);
        Type listType = new TypeToken<Collection<String>>() {}.getType();
        Collection<String> idList = new Gson().fromJson(favorites, listType);
        return new ArrayList<>(idList);
    }

    public void addFavorite(String serverId) {
        ArrayList<String> favorites = getFavoriteCatPosts();
        favorites.add(serverId);
        String array = gson.toJson(favorites);

        currentUser().setFavorites(array);
    }
    public void removeFavorite(String serverId) {
        ArrayList<String> favorites = getFavoriteCatPosts();
        favorites.remove(serverId);
        currentUser().setFavorites(Arrays.toString(favorites.toArray()));
    }

    public User currentUser() {
        User user = getUserDao().queryBuilder().where(UserDao.Properties.LoggedIn.eq(true)).unique();
        return user;
    }

    public UserEntity getLoggedInUser() {
        User user = currentUser();
        return UserMapper.fromUser(user);
    }
}
