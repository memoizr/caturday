package com.lovecats.catlover.ui.stream.interactor;

import com.lovecats.catlover.ui.stream.data.repository.CatPostRepository;

import java.util.Collection;

import greendao.CatPost;
import retrofit.Callback;

public class CatStreamInteractorImpl implements CatStreamInteractor {

    CatPostRepository catPostRepository;
    public CatStreamInteractorImpl(CatPostRepository catPostRepository) {
        this.catPostRepository = catPostRepository;
    }

    @Override
    public void getCatPostPageAndType(final int page, final String streamType, final Callback<Collection<CatPost>> callback) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                Collection<CatPost> catPostCollection =
                        catPostRepository.getCatPostsForPageAndCategory(page, streamType);
                callback.success(catPostCollection, null);
            }
        }).start();
    }
}
