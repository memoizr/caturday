package com.lovecats.catlover.models.session;

import com.lovecats.catlover.capsules.common.Config;
import com.lovecats.catlover.models.session.api.SessionApi;
import com.lovecats.catlover.models.session.datastore.SessionCloudDataStore;
import com.lovecats.catlover.models.session.datastore.SessionLocalDataStore;
import com.lovecats.catlover.models.session.db.SessionORM;
import com.lovecats.catlover.models.session.repository.SessionRepository;
import com.lovecats.catlover.models.session.repository.SessionRepositoryImpl;
import com.lovecats.catlover.models.user.datastore.UserLocalDataStore;
import com.lovecats.catlover.models.user.db.UserORM;

import javax.inject.Singleton;

import dagger.Provides;
import greendao.DaoSession;
import retrofit.RestAdapter;

public class SessionModule {

    @Provides
    @Singleton
    public SessionApi provideSessionApi() {
        String endpoint = Config.getEndpoint();

        RestAdapter adapter = new RestAdapter.Builder()
                .setEndpoint(endpoint)
                .build();

        return adapter.create(SessionApi.class);
    }

    @Provides @Singleton
    public SessionORM provideUserORM(DaoSession daoSession) {
        return new SessionORM(daoSession);
    }

    @Provides @Singleton
    public SessionCloudDataStore provideSessionCloudDataStore(SessionApi sessionApi) {
        return new SessionCloudDataStore(sessionApi);
    }

    @Provides @Singleton
    public SessionLocalDataStore provideSessionLocalDataStore(SessionORM sessionORM) {
        return new SessionLocalDataStore(sessionORM);
    }

    @Provides @Singleton
    public SessionRepository provideSessionRepository(SessionLocalDataStore sessionLocalDataStore,
                                                      SessionCloudDataStore sessionCloudDataStore,
                                                      DaoSession daoSession) {
        return new SessionRepositoryImpl(sessionLocalDataStore,
                sessionCloudDataStore,
                new UserORM(daoSession));
    }
}
