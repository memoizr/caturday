package com.lovecats.catlover.ui.stream.data.datastore;

import com.lovecats.catlover.common.Config;
import com.lovecats.catlover.ui.stream.api.CatPostApi;
import com.lovecats.catlover.ui.stream.data.CatPostEntity;
import com.lovecats.catlover.ui.stream.data.mapper.CatPostMapper;

import java.util.Collection;

import greendao.CatPost;
import retrofit.RestAdapter;

public class CatStreamCloudDataStore implements CatPostDataStore {

    @Override
    public Collection<CatPost> getCatPostsForPageAndCategory(int page, String category) {
        String endpoint = Config.getEndpoint();

        RestAdapter adapter = new RestAdapter.Builder()
                .setEndpoint(endpoint)
                .build();

        final CatPostApi api = adapter.create(CatPostApi.class);

        Collection<CatPostEntity> catPostEntities = api.getPosts(page, category);

        return CatPostMapper.transform(catPostEntities);
    }

    @Override
    public CatPost getCatPost(String serverId) {
        return null;
    }
}
