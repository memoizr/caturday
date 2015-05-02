package com.caturday.app.models.catpost;

import com.caturday.app.models.catpost.api.CatPostApi;
import com.caturday.app.models.catpost.datastore.CatPostCloudDataStore;
import com.caturday.app.models.catpost.datastore.CatPostLocalDataStore;
import com.caturday.app.models.catpost.db.CatPostDb;
import com.caturday.app.models.catpost.db.CatPostORM;
import com.caturday.app.models.catpost.repository.CatPostRepository;
import com.caturday.app.models.catpost.repository.CatPostRepositoryImpl;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import greendao.DaoSession;

@Module(
        library = true,
        complete = false
)
public class CatPostModule {

        @Provides @Singleton
        public CatPostCloudDataStore provideCatPostCloudDataStore(CatPostApi catPostApi) {
                return new CatPostCloudDataStore(catPostApi);
        }

        @Provides @Singleton
        public CatPostLocalDataStore provideCatPostLocalDataStore(DaoSession daoSession) {
                CatPostDb catPostDb = new CatPostORM(daoSession);
                return new CatPostLocalDataStore(catPostDb);
        }

        @Provides @Singleton
        public CatPostRepository provideCatPostRepository(CatPostLocalDataStore catPostLocalDataStore,
                                                          CatPostCloudDataStore catPostCloudDataStore) {
                return new CatPostRepositoryImpl(catPostLocalDataStore, catPostCloudDataStore);
        }
}
