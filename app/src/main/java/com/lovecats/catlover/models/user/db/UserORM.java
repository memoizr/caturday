package com.lovecats.catlover.models.user.db;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.lovecats.catlover.models.user.UserEntity;
import com.lovecats.catlover.models.user.UserMapper;

import java.lang.reflect.Type;
import java.util.HashSet;

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

    public HashSet<String> getFavoriteCatPosts(){

        String favorites;
        favorites = currentUser().getFavorites();

        if (favorites == null) {
            favorites = "[]";
        }

        Type listType = new TypeToken<HashSet<String>>() {}.getType();
        HashSet<String> idList = new Gson().fromJson(favorites, listType);

        return idList;
    }

    public void addFavorite(String serverId) {

        HashSet<String> favorites = getFavoriteCatPosts();

        favorites.add(serverId);

        String arrayJson = gson.toJson(favorites);

        getUserDao().update(setFavorites(arrayJson));
    }

    public void removeFavorite(String serverId) {

        HashSet<String> favorites = getFavoriteCatPosts();

        favorites.remove(serverId);

        String arrayJson = gson.toJson(favorites);

        getUserDao().update(setFavorites(arrayJson));
    }

    private User currentUser() {

        return getUserDao().queryBuilder().where(UserDao.Properties.LoggedIn.eq(true)).unique();
    }

    private User setFavorites(String string) {

        User user = currentUser();
        user.setFavorites(string);

        return user;
    }

    public UserEntity getLoggedInUser() {

        User user = currentUser();

        return UserMapper.fromUser(user);
    }
}
