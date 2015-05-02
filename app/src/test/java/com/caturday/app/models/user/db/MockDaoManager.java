package com.caturday.app.models.user.db;

import android.database.sqlite.SQLiteDatabase;


import greendao.DaoMaster;
import greendao.DaoSession;

public class MockDaoManager {
    public static DaoSession daoSession;
    public static SQLiteDatabase db;
    public static DaoMaster daoMaster;

    private MockDaoManager() {
    }

    public static DaoSession setupDatabase(DaoMaster.DevOpenHelper helper) {
        db = helper.getWritableDatabase();
        daoMaster = new DaoMaster(db);
        daoSession = daoMaster.newSession();
        return daoSession;
    }
}
