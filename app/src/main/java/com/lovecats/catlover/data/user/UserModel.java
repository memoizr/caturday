package com.lovecats.catlover.data.user;

import com.google.gson.Gson;
import com.lovecats.catlover.data.DaoManager;

import java.util.ArrayList;
import java.util.Arrays;
import greendao.User;
import greendao.UserDao;
import lombok.Getter;
import lombok.Setter;

public class UserModel {
    @Getter @Setter private String id;
    @Getter @Setter private String email;
    @Getter @Setter private String username;
    @Getter @Setter private String image_url;

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
        Gson gson = new Gson();
        String[] idList = gson.fromJson(favorites, String[].class);

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

    public static User getLoggedInUser() {
        return getUserDao().queryBuilder().where(UserDao.Properties.LoggedIn.eq(true)).unique();
    }
}
