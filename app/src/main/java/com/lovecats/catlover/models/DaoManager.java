package com.lovecats.catlover.models;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import greendao.DaoMaster;
import greendao.DaoSession;

public class DaoManager {
    public static DaoSession daoSession;
    private static Context sessionContext;
    private static DaoManager instance = null;
    public static SQLiteDatabase db;
    public static DaoMaster daoMaster;

    private DaoManager(Context context){
        sessionContext = context;
    }

    public static DaoManager DaoLoader(Context c) {
        if (instance == null) {
            instance = new DaoManager(c);
            setupDatabase();
        }
        return instance;
    }

    private static void setupDatabase(){
        System.out.println("setup");
        DaoMaster.DevOpenHelper helper = new DaoMaster.DevOpenHelper(sessionContext, "fool-db", null);
        db = helper.getWritableDatabase();
        daoMaster = new DaoMaster(db);
        daoSession = daoMaster.newSession();
    }

    public static DaoSession getDaoSession() {
        return daoSession;
    }
}
