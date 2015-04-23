package com.lovecats.catlover.models.catpost.datastore;

import com.lovecats.catlover.models.catpost.CatPostEntity;
import com.lovecats.catlover.models.catpost.db.CatPostDb;

import java.util.Collection;
import java.util.HashSet;

import rx.Observable;

public class CatPostLocalDataStore implements CatPostDataStore {

    private CatPostDb catPostDb;

    public CatPostLocalDataStore(CatPostDb catPostDb) {
        this.catPostDb = catPostDb;
    }

    @Override
    public Collection<CatPostEntity> getCatPostsForPageAndCategory(int page, String category) {
        return catPostDb.getPostsForPageAndCategory(page, category);
    }

    @Override
    public Collection<CatPostEntity> getCatPostsForServerIds(HashSet<String> ids) {
        return catPostDb.getCatPostsForServerIds(ids);
    }

    @Override
    public Observable<CatPostEntity> getCatPost(String serverId) {
        return catPostDb.getCatPostForServerId(serverId);
    }

    @Override
    public Collection<CatPostEntity> getRandomCatPosts(int howMany) {
        return catPostDb.getRandomCatPosts(howMany);
    }

    public void createMultipleCatPost(Collection<CatPostEntity> catPostEntityCollection) {
        catPostDb.createMultiplePost(catPostEntityCollection);
    }

    public void eraseCache() {
        catPostDb.eraseCache();
    }
}
