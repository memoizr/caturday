package com.lovecats.catlover;

import android.app.Application;

import com.lovecats.catlover.api.ApiModule;
import com.lovecats.catlover.capsules.common.ConfigModule;
import com.lovecats.catlover.capsules.dashboard.stream.interactor.CatStreamInteractorModule;
import com.lovecats.catlover.data.DataModule;
import com.lovecats.catlover.util.concurrent.ThreadModule;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module(
        injects = {
                App.class
        },
        includes = {
                ConfigModule.class,
                DataModule.class,
                CatStreamInteractorModule.class,
                ThreadModule.class,
                ApiModule.class
        }
)
public class AppModule {

    private App app;

    public AppModule(App app) {
        this.app = app;
    }

    @Provides @Singleton
    public Application provideApplication() {
        return app;
    }
}
