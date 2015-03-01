package com.lovecats.catlover.data;

import android.content.Context;

import java.util.List;

import greendao.User;
import greendao.UserDao;

/**
 * Created by user on 23/02/15.
 */
public class UserModel {
    public static void insertOrUpdate(Context context, User user) {
        getUserDao(context).insertOrReplace(user);
    }

    private static UserDao getUserDao(Context c) {
        return DaoManager.DaoLoader(c).getDaoSession().getUserDao();
    }

    public static long getCount(Context context) {
        return getUserDao(context).count();
    }

    public static void logInUser(Context context, long id) {
        getUserForId(context, id).setLoggedIn(true);
    }

    public static void logOutUser(Context context, long id) {
        getUserForId(context, id).setLoggedIn(false);
    }

    public static User getLoggedInUser(Context context) {
        return getUserDao(context).queryBuilder()
                .where(UserDao.Properties.LoggedIn.eq(true)).list().get(0);
    }

    public static List<User> getAllUsers(Context context) {
        return getUserDao(context).loadAll();
    }

    public static User getUserForId(Context context, long id) {
        return getUserDao(context).load(id);
    }
}
