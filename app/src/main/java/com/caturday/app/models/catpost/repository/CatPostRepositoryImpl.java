package com.caturday.app.models.catpost.repository;

import com.caturday.app.models.catpost.CatPostEntity;
import com.caturday.app.models.catpost.datastore.CatPostDataStore;
import com.caturday.app.models.catpost.datastore.CatPostLocalDataStore;
import com.caturday.app.models.catpost.datastore.CatPostCloudDataStore;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;

import rx.Observable;

public class CatPostRepositoryImpl implements CatPostRepository {

    private CatPostLocalDataStore catPostLocalDataStore;
    private CatPostCloudDataStore catPostCloudDataStore;

    public CatPostRepositoryImpl(CatPostLocalDataStore catPostLocalDataStore, CatPostCloudDataStore catPostCloudDataStore) {
        this.catPostLocalDataStore = catPostLocalDataStore;
        this.catPostCloudDataStore = catPostCloudDataStore;
    }

    private CatPostDataStore catPostFactory(boolean fromNetwork) {
        if (fromNetwork)
            return catPostCloudDataStore;
        else
            return catPostLocalDataStore;
    }

    @Override
    public Observable<List<CatPostEntity>> getCatPostsForPageAndCategory(int page, String category, boolean fromNetwork) {

//        Collection<CatPostEntity> catPostEntities = retrieveCatPosts(page, category, fromNetwork);
//
//        if (catPostEntities.size() == 0) {
//            catPostEntities = retrieveCatPosts(page, category, true);
//        }
//
//        if (fromNetwork)
//            catPostLocalDataStore.createMultipleCatPost(catPostEntities);

        Observable<List<CatPostEntity>> catPostEntities =
                catPostLocalDataStore.getCatPostsForPageAndCategory(page, category)
                .flatMap(collection -> {
            if (collection.size() > 0) {
                return Observable.just(collection);
            } else {
                return catPostCloudDataStore.getCatPostsForPageAndCategory(page, category).doOnNext(
                        catPostLocalDataStore::createMultipleCatPost
                );
            }
        });

        return catPostEntities;
    }

//    private Collection<CatPostEntity> retrieveCatPosts(int page, String category, boolean fromNetwork) {
//
//        CatPostDataStore catPostDataStore = catPostFactory(fromNetwork);
//        Collection<CatPostEntity> catPostEntities = catPostDataStore.getCatPostsForPageAndCategory(page, category);
//
//        if (fromNetwork)
//            catPostLocalDataStore.createMultipleCatPost(catPostEntities);
//
//        return catPostEntities;
//    }

    @Override
    public Observable<Collection<CatPostEntity>> getCatPostsForIds(HashSet<String> ids) {
        Collection<CatPostEntity> catPostEntities = catPostLocalDataStore.getCatPostsForServerIds(ids);
        return Observable.just(catPostEntities);
    }

    @Override
    public Observable<CatPostEntity> getCatPost(String serverId) {
        return catPostLocalDataStore.getCatPost(serverId);
    }

    @Override
    public Collection<CatPostEntity> getRandomCatPosts(int howMany) {
        return catPostLocalDataStore.getRandomCatPosts(howMany);
    }

    @Override
    public Observable<CatPostEntity> createPost(CatPostEntity catPostEntity) {
        return catPostCloudDataStore.createPost(catPostEntity);
    }

    @Override
    public void eraseCache() {
        catPostLocalDataStore.eraseCache();
    }
}
