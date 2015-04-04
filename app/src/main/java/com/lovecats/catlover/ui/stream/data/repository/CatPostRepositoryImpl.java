package com.lovecats.catlover.ui.stream.data.repository;

import com.lovecats.catlover.ui.stream.data.CatPostEntity;
import com.lovecats.catlover.ui.stream.data.datastore.CatPostDataStore;
import com.lovecats.catlover.ui.stream.data.datastore.CatPostLocalDataStore;
import com.lovecats.catlover.ui.stream.data.datastore.CatStreamCloudDataStore;

import java.util.Collection;

public class CatPostRepositoryImpl implements CatPostRepository{

    private final CatPostDataStore catPostDataStore;
    private final CatPostLocalDataStore catPostLocalDataStore;
    private final CatStreamCloudDataStore catStreamCloudDataStore;

    public CatPostRepositoryImpl(CatPostDataStore catPostDataStore) {
        this.catPostDataStore = catPostDataStore;
        this.catPostLocalDataStore = new CatPostLocalDataStore();
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
        return catPostDataStore.getCatPost(serverId);
    }
}
