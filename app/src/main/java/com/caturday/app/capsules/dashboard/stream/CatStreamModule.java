package com.caturday.app.capsules.dashboard.stream;

import com.caturday.app.AppModule;
import com.caturday.app.capsules.dashboard.stream.interactor.CatStreamInteractor;
import com.caturday.app.capsules.dashboard.stream.presenter.CatStreamPresenter;
import com.caturday.app.capsules.dashboard.stream.presenter.CatStreamPresenterImpl;
import com.caturday.app.capsules.dashboard.stream.view.CatStreamFragment;
import com.caturday.app.capsules.dashboard.stream.view.CatStreamView;
import com.squareup.otto.Bus;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module(
        injects = {
                CatStreamFragment.class
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

    @Provides
    public CatStreamPresenter provideCatStreamPresenter(CatStreamView catStreamView,
                                                        CatStreamInteractor catStreamInteractor,
                                                        Bus eventBus) {
        return new CatStreamPresenterImpl(catStreamView, catStreamInteractor, eventBus);
    }
}
