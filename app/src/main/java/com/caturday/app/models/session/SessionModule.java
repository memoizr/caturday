package com.caturday.app.models.session;

import com.caturday.app.capsules.common.Config;
import com.caturday.app.models.session.api.SessionApi;
import com.caturday.app.models.session.datastore.SessionCloudDataStore;
import com.caturday.app.models.session.datastore.SessionLocalDataStore;
import com.caturday.app.models.session.db.SessionORM;
import com.caturday.app.models.session.repository.SessionRepository;
import com.caturday.app.models.session.repository.SessionRepositoryImpl;
import com.caturday.app.models.user.db.UserORM;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import greendao.DaoSession;
import retrofit.RestAdapter;

@Module(
        library = true,
        complete = false
)
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
