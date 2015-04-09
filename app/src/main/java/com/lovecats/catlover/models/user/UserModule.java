package com.lovecats.catlover.models.user;

import com.lovecats.catlover.models.user.db.UserORM;
import com.lovecats.catlover.models.user.repository.UserRepository;
import com.lovecats.catlover.models.user.repository.UserRepositoryImpl;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import greendao.DaoSession;

@Module(
        library = true,
        complete = false
)
public class UserModule {

        @Provides @Singleton public UserORM provideUserORM(DaoSession daoSession) {
                return new UserORM(daoSession);
        }

        @Provides @Singleton public UserRepository provideUserRepository(UserORM userORM) {
                return new UserRepositoryImpl(userORM);
        }
}
