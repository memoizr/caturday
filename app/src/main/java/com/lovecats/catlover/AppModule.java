package com.lovecats.catlover;

import android.app.Application;
import android.content.Context;

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

    @Provides
    public Application provideApplication() {
        return app;
    }
}
