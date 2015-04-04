package com.lovecats.catlover.ui.stream.data.datastore;

import com.lovecats.catlover.common.Config;
import com.lovecats.catlover.ui.stream.api.CatPostApi;
import com.lovecats.catlover.ui.stream.data.CatPostEntity;

import java.util.Collection;

import retrofit.RestAdapter;

public class CatStreamCloudDataStore implements CatPostDataStore {

    @Override
    public Collection<CatPostEntity> getCatPostsForPageAndCategory(int page, String category) {
        String endpoint = Config.getEndpoint();

        RestAdapter adapter = new RestAdapter.Builder()
                .setEndpoint(endpoint)
                .build();

        final CatPostApi api = adapter.create(CatPostApi.class);

        Collection<CatPostEntity> catPostEntities = api.getPosts(page, category);

        return catPostEntities;
    }

    @Override
    public CatPostEntity getCatPost(String serverId) {
        return null;
    }
}
