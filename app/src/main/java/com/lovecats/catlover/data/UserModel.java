package com.lovecats.catlover.data;

import android.content.Context;

import java.util.List;

import greendao.User;
import greendao.UserDao;

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

    private static void deleteUser(String id) {
        getUserDao().delete(getUserForServerId(id));
    }

    public static User getUserForServerId(String id) {
        return getUserDao().queryBuilder().where(UserDao.Properties.ServerId.eq(id)).unique();
    }
}
