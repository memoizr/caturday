package com.lovecats.catlover.models.catpost.datastore;

import com.lovecats.catlover.capsules.common.Config;
import com.lovecats.catlover.capsules.dashboard.stream.api.CatPostApi;
import com.lovecats.catlover.models.catpost.CatPostEntity;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;

import retrofit.RestAdapter;

public class CatStreamCloudDataStore implements CatPostDataStore {

    public CatStreamCloudDataStore() {
    }

    @Override
    public Collection<CatPostEntity> getCatPostsForPageAndCategory(int page, String category) {
        String endpoint = Config.getEndpoint();

        RestAdapter adapter = new RestAdapter.Builder()
                .setEndpoint(endpoint)
                .build();

        final CatPostApi api = adapter.create(CatPostApi.class);
        Collection<CatPostEntity> catPostEntities = new ArrayList<>();
        try {
            catPostEntities = api.getPosts(page, category);
        } catch (Exception e) {

        }

        return catPostEntities;
    }

    @Override
    public Collection<CatPostEntity> getCatPostsForServerIds(HashSet<String> ids) {
        return new HashSet<>();
    }

    @Override
    public CatPostEntity getCatPost(String serverId) {
        return null;
    }

    @Override
    public Collection<CatPostEntity> getRandomCatPosts(int howMany) {
        return null;
    }
}
