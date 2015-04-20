package com.lovecats.catlover.models.catpost;

import com.lovecats.catlover.models.catpost.api.CatPostApi;
import com.lovecats.catlover.models.catpost.datastore.CatPostCloudDataStore;
import com.lovecats.catlover.models.catpost.datastore.CatPostLocalDataStore;
import com.lovecats.catlover.models.catpost.db.CatPostDb;
import com.lovecats.catlover.models.catpost.db.CatPostORM;
import com.lovecats.catlover.models.catpost.repository.CatPostRepository;
import com.lovecats.catlover.models.catpost.repository.CatPostRepositoryImpl;

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
