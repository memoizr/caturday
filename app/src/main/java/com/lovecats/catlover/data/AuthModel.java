package com.lovecats.catlover.data;

import android.content.Context;

import java.util.List;

import greendao.Auth;
import greendao.AuthDao;

public class AuthModel {
    public static void insertOrUpdate(Context context, Auth auth) {
        getAuthDao(context).insertOrReplace(auth);
    }

    private static AuthDao getAuthDao(Context c) {
        return DaoManager.DaoLoader(c).getDaoSession().getAuthDao();
    }

    public static long getCount(Context context) {
        return getAuthDao(context).count();
    }

    public static List<Auth> getAllAuths(Context context) {
        return getAuthDao(context).loadAll();
    }

    public static Auth getAuthForId(Context context, long id) {
        return getAuthDao(context).load(id);
    }
}
