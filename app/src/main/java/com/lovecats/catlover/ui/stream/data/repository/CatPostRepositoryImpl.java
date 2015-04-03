package com.lovecats.catlover.ui.stream.data.repository;

import com.lovecats.catlover.ui.stream.data.datastore.CatPostDataStore;
import com.lovecats.catlover.ui.stream.data.datastore.CatPostLocalDataStore;
import com.lovecats.catlover.ui.stream.data.datastore.CatStreamCloudDataStore;

import java.util.Collection;
import greendao.CatPost;

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
    public Collection<CatPost> getCatPostsForPageAndCategory(int page, String category) {

        return catStreamCloudDataStore.getCatPostsForPageAndCategory(page, category);
    }

    @Override
    public CatPost getCatPost(String serverId) {
        return catPostDataStore.getCatPost(serverId);

    }
}
