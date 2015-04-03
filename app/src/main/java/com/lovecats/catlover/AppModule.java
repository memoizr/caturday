package com.lovecats.catlover;

import android.app.Application;
import android.content.Context;

import com.lovecats.catlover.common.ConfigModule;
import com.lovecats.catlover.interactors.InteractorsModule;
import com.lovecats.catlover.ui.stream.interactor.CatStreamInteractor;
import com.lovecats.catlover.ui.stream.interactor.CatStreamInteractorModule;
import com.lovecats.catlover.util.concurrent.ThreadModule;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by user on 24/03/15.
 */

@Module(
        injects = {
                App.class
        },
        includes = {
                ConfigModule.class,
                CatStreamInteractorModule.class,
                ThreadModule.class,
                InteractorsModule.class
        }
)
public class AppModule {

    private Context context;
    private App app;

    public AppModule(Context context) {
        this.context = context;
    }

    public AppModule(App app) {
        this.app = app;
    }

//    @Provides
//    @Singleton
//    public Context provideContext(){
//        return context;
//    }

    @Provides @Singleton
    public Application provideApplication() {
        return app;
    }
}
