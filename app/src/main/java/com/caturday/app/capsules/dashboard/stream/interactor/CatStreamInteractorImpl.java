package com.caturday.app.capsules.dashboard.stream.interactor;

import com.caturday.app.models.catpost.CatPostEntity;
import com.caturday.app.models.catpost.repository.CatPostRepository;
import com.caturday.app.util.concurrent.PostExecutionThread;
import com.caturday.app.util.concurrent.ThreadExecutor;

import java.util.Collection;

import retrofit.Callback;

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
    public void getCatPostPageAndType(final int page,
                                      final String streamType,
                                      boolean fromNetwork,
                                      final Callback<Collection<CatPostEntity>> callback) {

        threadExecutor.execute(() -> {
            try {
                final Collection<CatPostEntity> catPostCollection =
                        catPostRepository.getCatPostsForPageAndCategory(page, streamType, fromNetwork);

            postExecutionThread.post(() -> callback.success(catPostCollection, null));
            } catch (Error e) {
                e.printStackTrace();
            }
        });
    }

    @Override
    public void eraseCache() {
        catPostRepository.eraseCache();
    }
}
