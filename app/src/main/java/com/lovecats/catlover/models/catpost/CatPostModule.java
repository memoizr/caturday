package com.lovecats.catlover.models.catpost;

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

        @Provides
        @Singleton
        public CatPostRepository provideCatPostRepository(DaoSession daoSession) {
                CatPostDb catPostDb = new CatPostORM(daoSession);
                return new CatPostRepositoryImpl(catPostDb);
        }
}
