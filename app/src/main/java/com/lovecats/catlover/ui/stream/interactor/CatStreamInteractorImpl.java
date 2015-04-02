package com.lovecats.catlover.ui.stream.interactor;

import com.lovecats.catlover.ui.stream.data.repository.CatPostRepository;

import java.util.Collection;

import greendao.CatPost;

public class CatStreamInteractorImpl implements CatStreamInteractor {

    CatPostRepository catPostRepository;
    public CatStreamInteractorImpl(CatPostRepository catPostRepository) {
        this.catPostRepository = catPostRepository;
    }

    @Override
    public Collection<CatPost> getCatPostPageAndType(int page, String streamType) {
        return catPostRepository.getCatPostsForPageAndCategory(page, streamType);
    }
}
