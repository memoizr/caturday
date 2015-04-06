package com.lovecats.catlover.ui.profile.data.db;

import com.lovecats.catlover.data.DaoManager;
import com.lovecats.catlover.ui.profile.data.UserEntity;
import com.lovecats.catlover.ui.profile.data.UserMapper;

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

//    public static ArrayList<String> getFavoriteCatPosts(){
//        String favorites = getLoggedInUser().getFavorites();
//        Gson gson = new Gson();
//        String[] idList = gson.fromJson(favorites, String[].class);
//
//        return new ArrayList<>(Arrays.asList(idList));
//    }

//    public static void addFavorite(String serverId) {
//        ArrayList<String> favorites = getFavoriteCatPosts();
//        favorites.add(serverId);
//        getLoggedInUser().setFavorites(Arrays.toString(favorites.toArray()));
//    }
//
//    public static void removeFavorite(String serverId) {
//        ArrayList<String> favorites = getFavoriteCatPosts();
//        favorites.remove(serverId);
//        getLoggedInUser().setFavorites(Arrays.toString(favorites.toArray()));
//    }

    public static UserEntity getLoggedInUser() {
        User user = getUserDao().queryBuilder().where(UserDao.Properties.LoggedIn.eq(true)).unique();
        return UserMapper.fromUser(user);
    }
}
