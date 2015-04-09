package com.lovecats.catlover.models.catpost.repository;

import com.lovecats.catlover.models.catpost.CatPostEntity;
import com.lovecats.catlover.models.catpost.datastore.CatPostDataStore;
import com.lovecats.catlover.models.catpost.datastore.CatPostLocalDataStore;
import com.lovecats.catlover.models.catpost.datastore.CatStreamCloudDataStore;
import com.lovecats.catlover.models.catpost.db.CatPostDb;

import java.util.Collection;
import java.util.HashSet;

public class CatPostRepositoryImpl implements CatPostRepository {

    private CatPostLocalDataStore catPostLocalDataStore;
    private CatStreamCloudDataStore catStreamCloudDataStore;

    // TODO fix this sham!

    public CatPostRepositoryImpl(CatPostDb catPostDb) {
        this.catPostLocalDataStore = new CatPostLocalDataStore(catPostDb);
        this.catStreamCloudDataStore = new CatStreamCloudDataStore();
    }

    private CatPostDataStore catPostFactory(boolean fromNetwork) {
        if (fromNetwork)
            return new CatStreamCloudDataStore();
        else
            return catPostLocalDataStore;
    }

    @Override
    public Collection<CatPostEntity> getCatPostsForPageAndCategory(int page, String category, boolean fromNetwork) {

        System.out.println("been here" + fromNetwork);
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
}
