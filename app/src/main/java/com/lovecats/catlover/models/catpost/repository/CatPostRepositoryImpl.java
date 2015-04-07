package com.lovecats.catlover.models.catpost.repository;

import com.lovecats.catlover.models.catpost.CatPostEntity;
import com.lovecats.catlover.models.catpost.datastore.CatPostDataStore;
import com.lovecats.catlover.models.catpost.datastore.CatPostLocalDataStore;
import com.lovecats.catlover.models.catpost.datastore.CatStreamCloudDataStore;
import com.lovecats.catlover.models.catpost.db.CatPostDb;

import java.util.Collection;

import greendao.DaoSession;

public class CatPostRepositoryImpl implements CatPostRepository {

    private final CatPostLocalDataStore catPostLocalDataStore;
    private final CatStreamCloudDataStore catStreamCloudDataStore;

    public CatPostRepositoryImpl(CatPostDb catPostDb) {
        this.catPostLocalDataStore = new CatPostLocalDataStore(catPostDb);
        this.catStreamCloudDataStore = new CatStreamCloudDataStore();
    }

    @Override
    public Collection<CatPostEntity> getCatPostsForPageAndCategory(int page, String category) {
        Collection<CatPostEntity> catPostEntities = catPostLocalDataStore.getCatPostsForPageAndCategory(page, category);
        catPostLocalDataStore.createMultipleCatPost(catPostEntities);
        return catPostEntities;
    }

    @Override
    public CatPostEntity getCatPost(String serverId) {
        return catPostLocalDataStore.getCatPost(serverId);
    }
}
