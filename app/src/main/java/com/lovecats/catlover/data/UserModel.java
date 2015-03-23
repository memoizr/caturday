package com.lovecats.catlover.data;

import org.json.JSONArray;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

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
        long id = getUserDao().insertOrReplace(user);
        return getUserDao().loadByRowId(id);
    }

    private static UserDao getUserDao() {
        return DaoManager.getDaoSession().getUserDao();
    }

    public static void flushUsers() {
        getUserDao().deleteAll();
    }

    public static boolean userLoggedIn() {
        return getUserDao().count() > 0;
    }

    public static List<String> getFavoriteCatPosts(){
        String favorites = getLoggedInUser().getFavorites();
        List<String> idList = new ArrayList();
        try {
            JSONArray jsonArray = new JSONArray(favorites);
            for (int i = 0; i < jsonArray.length(); i++) {
                idList.add(jsonArray.getString(i));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return idList;
    }

    public static void addFavorite(String serverId) {
        List<String> favorites = getFavoriteCatPosts();
        favorites.add(serverId);
        getLoggedInUser().setFavorites(Arrays.toString(favorites.toArray()));
    }

    public static void removeFavorite(String serverId) {
        List<String> favorites = getFavoriteCatPosts();
        favorites.remove(serverId);
        getLoggedInUser().setFavorites(Arrays.toString(favorites.toArray()));
    }


























    public static User getLoggedInUser() {
        return getUserDao().queryBuilder().where(UserDao.Properties.LoggedIn.eq(true)).unique();
    }
}
