package com.caturday.app.models.catpost.datastore;

import com.caturday.app.models.catpost.CatPostEntity;
import com.caturday.app.models.catpost.db.CatPostDb;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;

import rx.Observable;

public class CatPostLocalDataStore implements CatPostDataStore {

    private CatPostDb catPostDb;

    public CatPostLocalDataStore(CatPostDb catPostDb) {
        this.catPostDb = catPostDb;
    }

    @Override
    public Observable<List<CatPostEntity>> getCatPostsForPageAndCategory(int page, String category) {
        return Observable.just(new ArrayList(catPostDb.getPostsForPageAndCategory(page, category)));
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
