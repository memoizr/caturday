package com.caturday.app.capsules.dashboard.stream.interactor;

import com.caturday.app.models.catpost.CatPostEntity;
import com.caturday.app.models.catpost.repository.CatPostRepository;
import com.caturday.app.util.concurrent.PostExecutionThread;
import com.caturday.app.util.concurrent.ThreadExecutor;

import java.util.Collection;
import java.util.List;

import retrofit.Callback;
import rx.Observable;

public class CatStreamInteractorImpl implements CatStreamInteractor {

    private final PostExecutionThread postExecutionThread;
    private final ThreadExecutor threadExecutor;

    CatPostRepository catPostRepository;

    public CatStreamInteractorImpl(CatPostRepository catPostRepository,
                                   ThreadExecutor threadExecutor,
                                   PostExecutionThread postExecutionThread) {

        this.catPostRepository = catPostRepository;
        this.threadExecutor = threadExecutor;
        this.postExecutionThread = postExecutionThread;
    }

    @Override
    public Observable<List<CatPostEntity>> getCatPostPageAndType(final int page,
                                      final String streamType,
                                      boolean fromNetwork) {

        return catPostRepository.getCatPostsForPageAndCategory(page, streamType, fromNetwork);
    }

    @Override
    public void eraseCache() {
        catPostRepository.eraseCache();
    }
}
