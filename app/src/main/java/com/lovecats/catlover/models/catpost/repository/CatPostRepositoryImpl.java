package com.lovecats.catlover.models.catpost.repository;

import com.lovecats.catlover.models.catpost.CatPostEntity;
import com.lovecats.catlover.models.catpost.datastore.CatPostDataStore;
import com.lovecats.catlover.models.catpost.datastore.CatPostLocalDataStore;
import com.lovecats.catlover.models.catpost.datastore.CatPostCloudDataStore;
import com.lovecats.catlover.models.catpost.db.CatPostDb;

import java.util.Collection;
import java.util.HashSet;

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
    public Collection<CatPostEntity> getCatPostsForPageAndCategory(int page, String category, boolean fromNetwork) {

        Collection<CatPostEntity> catPostEntities = retrieveCatPosts(page, category, fromNetwork);

        if (catPostEntities.size() == 0) {
            fromNetwork = true;
            catPostEntities = retrieveCatPosts(page, category, fromNetwork);
        }

        if (fromNetwork)
            catPostLocalDataStore.createMultipleCatPost(catPostEntities);

        return catPostEntities;
    }

    private Collection<CatPostEntity> retrieveCatPosts(int page, String category, boolean fromNetwork) {

        CatPostDataStore catPostDataStore = catPostFactory(fromNetwork);
        Collection<CatPostEntity> catPostEntities = catPostDataStore.getCatPostsForPageAndCategory(page, category);

        if (fromNetwork)
            catPostLocalDataStore.createMultipleCatPost(catPostEntities);

        return catPostEntities;
    }



    @Override
    public Collection<CatPostEntity> getCatPostsForIds(HashSet<String> ids) {
        Collection<CatPostEntity> catPostEntities = catPostLocalDataStore.getCatPostsForServerIds(ids);
        return catPostEntities;
    }

    @Override
    public CatPostEntity getCatPost(String serverId) {
        return catPostLocalDataStore.getCatPost(serverId);
    }

    @Override
    public Collection<CatPostEntity> getRandomCatPosts(int howMany) {
        return catPostLocalDataStore.getRandomCatPosts(howMany);
    }

    @Override
    public Observable<CatPostEntity> createPost(CatPostEntity catPostEntity) {
        System.out.println("been here");
        return catPostCloudDataStore.createPost(catPostEntity);
    }
}
