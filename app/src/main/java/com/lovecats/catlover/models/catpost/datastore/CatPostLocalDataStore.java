package com.lovecats.catlover.models.catpost.datastore;

import com.lovecats.catlover.models.catpost.CatPostEntity;
import com.lovecats.catlover.models.catpost.db.CatPostDb;
import com.lovecats.catlover.models.catpost.db.CatPostORM;

import java.util.Collection;

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
    public CatPostEntity getCatPost(String serverId) {
        return catPostDb.getCatPostForServerId(serverId);
    }

    public void createMultipleCatPost(Collection<CatPostEntity> catPostEntityCollection) {
        catPostDb.createMultiplePost(catPostEntityCollection);
    }
}
