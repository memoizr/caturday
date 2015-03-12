package com.lovecats.catlover.data;

import android.content.Context;

import java.util.List;

import greendao.Auth;
import greendao.AuthDao;
import lombok.Getter;
import lombok.Setter;

public class AuthModel {
    @Setter @Getter private String username;
    @Setter @Getter private String id;
    @Setter @Getter private String email;

    public static void insertOrUpdate(Auth auth) {
        getAuthDao().insertOrReplace(auth);
    }

    private static AuthDao getAuthDao() {
        return DaoManager.getDaoSession().getAuthDao();
    }

    public static Auth getAuthToken() {
        return getAuthDao().loadAll().get(0);
    }

    public static void setAuthToken(String token) {
        Auth auth = new Auth();
        auth.setToken(token);
        insertOrUpdate(auth);
    }

}
