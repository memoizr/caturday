package com.lovecats.catlover.data;

import android.app.Application;
import android.content.Context;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import greendao.DaoSession;

/**
 * Created by Cat#2 on 07/04/15.
 */
@Module(
        library = true,
        complete = false
)
public class DataModule {
        @Provides
        @Singleton
        DaoSession provideDaoSession(){
                return DaoManager.getDaoSession();
        }
}
