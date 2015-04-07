package com.lovecats.catlover.capsules.dashboard.stream.interactor;

import com.lovecats.catlover.models.catpost.datastore.CatPostDataStore;
import com.lovecats.catlover.models.catpost.datastore.CatPostLocalDataStore;
import com.lovecats.catlover.models.catpost.db.CatPostDb;
import com.lovecats.catlover.models.catpost.db.CatPostORM;
import com.lovecats.catlover.models.catpost.repository.CatPostRepository;
import com.lovecats.catlover.models.catpost.repository.CatPostRepositoryImpl;
import com.lovecats.catlover.util.concurrent.PostExecutionThread;
import com.lovecats.catlover.util.concurrent.ThreadExecutor;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import greendao.DaoSession;

@Module(
        complete = false,
        library = true
)
public class CatStreamInteractorModule {

    @Provides @Singleton public CatPostRepository provideCatPostRepository(DaoSession daoSession) {
        CatPostDb catPostDb = new CatPostORM(daoSession);
        return new CatPostRepositoryImpl(catPostDb);
    }

    @Provides @Singleton public CatStreamInteractor provideCatStreamInteractor(
            CatPostRepository catPostRepository,
            ThreadExecutor threadExecutor,
            PostExecutionThread postExecutionThread ) {

        return new CatStreamInteractorImpl(catPostRepository, threadExecutor, postExecutionThread);
    }
}
