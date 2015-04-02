package com.lovecats.catlover.ui.stream;

import com.lovecats.catlover.AppModule;
import com.lovecats.catlover.ui.stream.interactor.CatStreamInteractor;
import com.lovecats.catlover.ui.stream.interactor.CatStreamInteractorImpl;
import com.lovecats.catlover.ui.stream.presenter.CatStreamPresenter;
import com.lovecats.catlover.ui.stream.presenter.CatStreamPresenterImpl;
import com.lovecats.catlover.ui.stream.view.CatStreamFragment;
import com.lovecats.catlover.ui.stream.view.CatStreamView;

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
    @Singleton
    public CatStreamPresenter provideCatStreamPresenter(CatStreamView catStreamView,
                                                        CatStreamInteractor catStreamInteractor) {
        return new CatStreamPresenterImpl(catStreamView, catStreamInteractor);
    }
}
