package com.lovecats.catlover.ui.detail.interactor;

import com.lovecats.catlover.ui.stream.data.CatPostEntity;
import com.lovecats.catlover.ui.stream.data.repository.CatPostRepository;
import com.lovecats.catlover.util.concurrent.PostExecutionThread;
import com.lovecats.catlover.util.concurrent.ThreadExecutor;
import com.lovecats.catlover.util.concurrent.WorkerCallback;

public class CatDetailInteractorImpl implements CatDetailInteractor {

    private final CatPostRepository catPostRepository;
    private final PostExecutionThread postExecutionThread;
    private final ThreadExecutor threadExecutor;

    public CatDetailInteractorImpl(CatPostRepository catPostRepository,
                                   ThreadExecutor threadExecutor,
                                   PostExecutionThread postExecutionThread) {
        this.catPostRepository = catPostRepository;
        this.threadExecutor = threadExecutor;
        this.postExecutionThread = postExecutionThread;
    }

    @Override
    public void getPostFromId(final String serverId, final WorkerCallback<CatPostEntity> callback) {
        threadExecutor.execute(new Runnable() {
            @Override
            public void run() {
                final CatPostEntity catPostEntity = catPostRepository.getCatPost(serverId);
                postExecutionThread.post(new Runnable() {
                    @Override
                    public void run() {
                        callback.done(catPostEntity);
                    }
                });

            }
        });
    }
}
