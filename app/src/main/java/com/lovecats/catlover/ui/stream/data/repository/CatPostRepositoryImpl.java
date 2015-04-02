package com.lovecats.catlover.ui.stream.data.repository;

import com.lovecats.catlover.ui.stream.data.datastore.CatPostDataStore;

import java.util.Collection;

import greendao.CatPost;

public class CatPostRepositoryImpl implements CatPostRepository{

    private final CatPostDataStore catPostDataStore;

    public CatPostRepositoryImpl(CatPostDataStore catPostDataStore) {
        this.catPostDataStore = catPostDataStore;
    }


    @Override
    public Collection<CatPost> getCatPostsForPageAndCategory(int page, String category) {
        return catPostDataStore.getCatPostsForPageAndCategory(page, category);
    }

    @Override
    public CatPost getCatPost(String serverId) {
        return catPostDataStore.getCatPost(serverId);
    }
}
