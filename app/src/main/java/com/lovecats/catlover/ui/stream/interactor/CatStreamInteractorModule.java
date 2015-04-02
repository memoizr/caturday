package com.lovecats.catlover.ui.stream.interactor;

import com.lovecats.catlover.ui.stream.data.datastore.CatPostDataStore;
import com.lovecats.catlover.ui.stream.data.datastore.CatPostLocalDataStore;
import com.lovecats.catlover.ui.stream.data.repository.CatPostRepository;
import com.lovecats.catlover.ui.stream.data.repository.CatPostRepositoryImpl;

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
            CatPostRepository catPostRepository) {
        return new CatStreamInteractorImpl(catPostRepository);
    }
}
