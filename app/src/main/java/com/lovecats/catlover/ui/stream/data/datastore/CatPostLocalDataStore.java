package com.lovecats.catlover.ui.stream.data.datastore;

import com.lovecats.catlover.ui.stream.data.CatPostEntity;
import com.lovecats.catlover.ui.stream.data.db.CatPostDb;
import com.lovecats.catlover.ui.stream.data.db.CatPostORM;

import java.util.Collection;

import greendao.CatPost;

public class CatPostLocalDataStore implements CatPostDataStore{

    CatPostDb catPostDb = new CatPostORM();

    @Override
    public Collection<CatPost> getCatPostsForPageAndCategory(int page, String category) {
        return catPostDb.getPostsForPageAndCategory(page, category);
    }

    @Override
    public CatPost getCatPost(String serverId) {
        return catPostDb.getCatPostForServerId(serverId);
    }

    public void createMultipleCatPost(Collection<CatPostEntity> catPostEntityCollection) {
        catPostDb.createMultiplePost(catPostEntityCollection);
    }
}
