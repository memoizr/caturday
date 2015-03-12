package com.lovecats.catlover.data;

import android.content.Context;

import java.math.BigInteger;
import java.util.List;

import greendao.User;
import greendao.UserDao;
import lombok.Getter;
import lombok.Setter;

public class UserModel {

    public static User insertOrUpdate(User user) {
        long id = getUserDao().insertOrReplace(user);
        return getUserDao().loadByRowId(id);
    }

    private static UserDao getUserDao() {
        return DaoManager.getDaoSession().getUserDao();
    }

    public static long getCount() {
        return getUserDao().count();
    }

    public static void logOutUser(String id) {
        deleteUser(id);
    }

    private static void deleteUser(String id) {
        getUserDao().delete(getUserForServerId(id));
    }

    public static User getLoggedInUser() {
        if (getCount() > 0) {
            return getUserDao().queryBuilder()
                    .where(UserDao.Properties.LoggedIn.eq(true)).list().get(0);
        } else {
            return null;
        }
    }

    public static List<User> getAllUsers(Context context) {
        return getUserDao().loadAll();
    }

    public static User getUserForId(long id) {
        return getUserDao().load(id);
    }

    public static User getUserForServerId(String id) {
        return getUserDao().queryBuilder().where(UserDao.Properties.ServerId.eq(id)).unique();
    }
}
