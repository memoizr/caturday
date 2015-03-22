package com.lovecats.catlover.data;

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

    public static User getLoggedInUser() {
        return getUserDao().queryBuilder().where(UserDao.Properties.LoggedIn.eq(true)).unique();
    }
}
