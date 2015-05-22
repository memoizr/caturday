package com.caturday.app.models.gcm;

import com.caturday.app.models.gcm.datastore.GcmCloudDataStore;
import com.caturday.app.models.gcm.datastore.GcmCloudDataStoreImpl;
import com.caturday.app.models.gcm.repository.GcmRepository;
import com.caturday.app.models.gcm.repository.GcmRepositoryImpl;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import retrofit.RestAdapter;

@Module(
        complete = false,
        library = true
)
public class GcmModule {

       @Provides @Singleton
        public GcmApi provideGcmApi(RestAdapter adapter) {
           return adapter.create(GcmApi.class);
       }

    @Provides @Singleton
    public GcmCloudDataStore provideGcmCloudDataStore(GcmApi api) {
        return new GcmCloudDataStoreImpl(api);
    }

    @Provides @Singleton
    public GcmRepository provideGcmRepository(GcmCloudDataStore cloudDataStore) {
        return new GcmRepositoryImpl(cloudDataStore);
    }
}
