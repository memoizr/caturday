package com.caturday.app.capsules.dashboard.stream;

import com.caturday.app.AppModule;
import com.caturday.app.capsules.dashboard.stream.interactor.CatStreamInteractor;
import com.caturday.app.capsules.dashboard.stream.interactor.CatStreamInteractorImpl;
import com.caturday.app.capsules.dashboard.stream.view.CatStreamPresenter;
import com.caturday.app.capsules.dashboard.stream.view.CatStreamPresenterImpl;
import com.caturday.app.capsules.dashboard.stream.view.CatStreamFragment;
import com.caturday.app.capsules.dashboard.stream.view.CatStreamView;
import com.caturday.app.models.catpost.CatPostModule;
import com.caturday.app.models.catpost.repository.CatPostRepository;
import com.caturday.app.models.user.UserModule;
import com.caturday.app.models.user.repository.UserRepository;
import com.caturday.app.models.vote.VoteModule;
import com.caturday.app.models.vote.repository.VoteRepository;
import com.squareup.otto.Bus;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module(
        injects = {
                CatStreamFragment.class
        },
        includes = {
                CatPostModule.class,
                VoteModule.class,
                UserModule.class
        },
        addsTo = AppModule.class
)
public class CatStreamModule {
    private CatStreamView catStreamView;

    public CatStreamModule(CatStreamView catStreamView) {
        this.catStreamView = catStreamView;
    }

    @Provides
    @Singleton
    public CatStreamView provideCatStreamView() {
        return catStreamView;
    }

    @Provides @Singleton public CatStreamInteractor provideCatStreamInteractor(
            CatPostRepository catPostRepository,
            VoteRepository voteRepository, UserRepository userRepository) {

        return new CatStreamInteractorImpl(catPostRepository, voteRepository, userRepository);
    }

    @Provides
    public CatStreamPresenter provideCatStreamPresenter(CatStreamView catStreamView,
                                                        CatStreamInteractor catStreamInteractor,
                                                        Bus eventBus) {
        return new CatStreamPresenterImpl(catStreamView, catStreamInteractor, eventBus);
    }
}
