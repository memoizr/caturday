package com.lovecats.catlover.common;

import android.app.Application;

import com.lovecats.catlover.common.Config;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by user on 24/03/15.
 */

@Module(
        complete = false,
        library = true
)
public class ConfigModule {

    @Provides @Singleton public Config provideConfig(Application app){
        return new Config(app);
    }
}
