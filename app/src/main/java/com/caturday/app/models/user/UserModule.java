package com.caturday.app.models.user;

import com.caturday.app.models.user.api.UserApi;
import com.caturday.app.models.user.datastore.UserCloudDataStore;
import com.caturday.app.models.user.db.UserORM;
import com.caturday.app.models.user.repository.UserRepository;
import com.caturday.app.models.user.repository.UserRepositoryImpl;

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
        public UserApi provideUserApi(RestAdapter adapter) {

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
