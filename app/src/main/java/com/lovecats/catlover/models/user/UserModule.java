package com.lovecats.catlover.models.user;

import com.lovecats.catlover.capsules.common.Config;
import com.lovecats.catlover.models.user.api.UserApi;
import com.lovecats.catlover.models.user.datastore.UserCloudDataStore;
import com.lovecats.catlover.models.user.datastore.UserLocalDataStore;
import com.lovecats.catlover.models.user.db.UserORM;
import com.lovecats.catlover.models.user.repository.UserRepository;
import com.lovecats.catlover.models.user.repository.UserRepositoryImpl;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import greendao.DaoSession;
import retrofit.RestAdapter;

@Module(
        library = true,
        complete = false
)
public class UserModule {

        @Provides
        @Singleton
        public UserApi provideUserApi() {
                String endpoint = Config.getEndpoint();

                RestAdapter adapter = new RestAdapter.Builder()
                        .setEndpoint(endpoint)
                        .build();
                final UserApi api = adapter.create(UserApi.class);
                return api;
        }

        @Provides @Singleton
        public UserORM provideUserORM(DaoSession daoSession) {
                return new UserORM(daoSession);
        }

        @Provides @Singleton
        public UserCloudDataStore provideUserCloudDataStore(UserApi userApi) {
                return new UserCloudDataStore(userApi);
        }

        @Provides @Singleton
        public UserRepository provideUserRepository(UserORM userORM, UserCloudDataStore userCloudDataStore) {
                return new UserRepositoryImpl(userORM, userCloudDataStore);
        }
}
