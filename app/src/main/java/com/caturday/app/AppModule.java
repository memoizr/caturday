package com.caturday.app;

import android.app.Application;
import android.content.Context;

import com.caturday.app.models.ApiModule;
import com.caturday.app.capsules.common.ConfigModule;
import com.caturday.app.capsules.common.events.BusModule;
import com.caturday.app.capsules.dashboard.stream.interactor.CatStreamInteractorModule;
import com.caturday.app.models.DataModule;
import com.caturday.app.util.concurrent.ThreadModule;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module(
        complete = false,
        library = true,
        injects = {
                App.class
        },
        includes = {
                ConfigModule.class,
                DataModule.class,
                CatStreamInteractorModule.class,
                ThreadModule.class,
                BusModule.class,
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

    @Provides @Singleton
    public Context provideContext() {
        return app.getBaseContext();
    }
}
