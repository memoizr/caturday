package com.caturday.app.capsules.dashboard.stream.interactor;

import com.caturday.app.models.catpost.CatPostModule;
import com.caturday.app.models.catpost.repository.CatPostRepository;
import com.caturday.app.models.vote.VoteModule;
import com.caturday.app.models.vote.repository.VoteRepository;
import com.caturday.app.util.concurrent.PostExecutionThread;
import com.caturday.app.util.concurrent.ThreadExecutor;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module(
        complete = false,
        includes = {
                CatPostModule.class,
                VoteModule.class
        },
        library = true
)
public class CatStreamInteractorModule {

    @Provides @Singleton public CatStreamInteractor provideCatStreamInteractor(
            CatPostRepository catPostRepository,
            VoteRepository voteRepository) {

        return new CatStreamInteractorImpl(catPostRepository, voteRepository);
    }
}
