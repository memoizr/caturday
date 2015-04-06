package com.lovecats.catlover.capsules.dashboard.stream.interactor;

import com.lovecats.catlover.models.catpost.datastore.CatPostDataStore;
import com.lovecats.catlover.models.catpost.datastore.CatPostLocalDataStore;
import com.lovecats.catlover.models.catpost.repository.CatPostRepository;
import com.lovecats.catlover.models.catpost.repository.CatPostRepositoryImpl;
import com.lovecats.catlover.util.concurrent.PostExecutionThread;
import com.lovecats.catlover.util.concurrent.ThreadExecutor;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module(
        complete = false,
        library = true
)
public class CatStreamInteractorModule {

    @Provides @Singleton public CatPostRepository provideCatPostRepository() {
        CatPostDataStore catPostDataStore = new CatPostLocalDataStore();
        return new CatPostRepositoryImpl(catPostDataStore);
    }

    @Provides @Singleton public CatStreamInteractor provideCatStreamInteractor(
            CatPostRepository catPostRepository,
            ThreadExecutor threadExecutor,
            PostExecutionThread postExecutionThread ) {

        return new CatStreamInteractorImpl(catPostRepository, threadExecutor, postExecutionThread);
    }
}
