package com.lovecats.catlover.models.user.db;

import com.google.gson.Gson;
import com.lovecats.catlover.data.DaoManager;
import com.lovecats.catlover.models.user.UserEntity;
import com.lovecats.catlover.models.user.UserMapper;

import java.util.ArrayList;
import java.util.Arrays;

import greendao.User;
import greendao.UserDao;

public class UserORM {

    public static User insertOrUpdate(User user) {
        flushUsers();
        user.setLoggedIn(true);
        long id = getUserDao().insertOrReplace(user);
        return getUserDao().loadByRowId(id);
    }

    private static UserDao getUserDao() {
        return DaoManager.getDaoSession().getUserDao();
    }

    public static void flushUsers() {
        getUserDao().deleteAll();
    }

    public boolean userLoggedIn() {
        return getUserDao().count() > 0;
    }

    public static ArrayList<String> getFavoriteCatPosts(){
        String favorites = getLoggedInUser().getFavorites();
        System.out.println(favorites);
        favorites = "[]";
        Gson gson = new Gson();
        String[] idList = gson.fromJson(favorites, String[].class);
        System.out.println(idList);

        return new ArrayList<>(Arrays.asList(idList));
    }

    public static void addFavorite(String serverId) {
        ArrayList<String> favorites = getFavoriteCatPosts();
        favorites.add(serverId);
        getLoggedInUser().setFavorites(Arrays.toString(favorites.toArray()));
    }

    public static void removeFavorite(String serverId) {
        ArrayList<String> favorites = getFavoriteCatPosts();
        favorites.remove(serverId);
        getLoggedInUser().setFavorites(Arrays.toString(favorites.toArray()));
    }

    public static UserEntity getLoggedInUser() {
        User user = getUserDao().queryBuilder().where(UserDao.Properties.LoggedIn.eq(true)).unique();
        return UserMapper.fromUser(user);
    }
}
