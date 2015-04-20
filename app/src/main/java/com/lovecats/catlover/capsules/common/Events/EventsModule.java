package com.lovecats.catlover.capsules.common.Events;

import com.squareup.otto.Bus;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module(
        complete = false,
        library = true
)
public class EventsModule {

    @Provides @Singleton Bus provideEventBus() {
        return new Bus();
    }
}
