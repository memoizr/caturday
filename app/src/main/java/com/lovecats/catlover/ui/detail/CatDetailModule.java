package com.lovecats.catlover.ui.detail;

import com.lovecats.catlover.AppModule;
import com.lovecats.catlover.ui.detail.interactor.CatDetailInteractor;
import com.lovecats.catlover.ui.detail.interactor.CatDetailInteractorImpl;
import com.lovecats.catlover.ui.detail.presenter.CatDetailPresenter;
import com.lovecats.catlover.ui.detail.presenter.CatDetailPresenterImpl;
import com.lovecats.catlover.ui.detail.view.CatDetailActivity;
import com.lovecats.catlover.ui.detail.view.CatDetailView;
import com.lovecats.catlover.ui.stream.data.repository.CatPostRepository;
import com.lovecats.catlover.util.concurrent.PostExecutionThread;
import com.lovecats.catlover.util.concurrent.ThreadExecutor;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;


@Module(
        injects = {
                CatDetailActivity.class
        },
        addsTo = AppModule.class
)
public class CatDetailModule {
    private CatDetailView catDetailView;

    public CatDetailModule(CatDetailView catDetailView) {
        this.catDetailView = catDetailView;
    }

    @Provides @Singleton public CatDetailView provideCatDetailView() {
        return catDetailView;
    }

    @Provides @Singleton public CatDetailInteractor provideCatDetailInteractor(
            CatPostRepository catPostRepository,
            ThreadExecutor threadExecutor,
            PostExecutionThread postExecutionThread) {
        return new CatDetailInteractorImpl(catPostRepository, threadExecutor, postExecutionThread);
    }

    @Provides @Singleton public CatDetailPresenter provideCatDetailPresenter(CatDetailView catDetailView,
                                                        CatDetailInteractor catDetailInteractor) {
        return new CatDetailPresenterImpl(catDetailView, catDetailInteractor);
    }
}
