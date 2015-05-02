package com.caturday.app.capsules.common.events;

import com.squareup.otto.Bus;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module(
        complete = false,
        library = true
)
public class BusModule {

    @Provides @Singleton Bus provideEventBus() {
        return new Bus();
    }
}
