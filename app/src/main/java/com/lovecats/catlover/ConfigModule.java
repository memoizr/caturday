package com.lovecats.catlover;

import android.app.Application;
import android.content.Context;

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
