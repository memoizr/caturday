package com.caturday.app.util.concurrent;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module(
        complete = false,
        library = true
)
public class ThreadModule {

    @Provides @Singleton ThreadExecutor provideThreadExecutor() {
        return new WorkExecutor();
    }

    @Provides @Singleton PostExecutionThread providePostExecutionThread() {
        return new MainThread();
    }
}
