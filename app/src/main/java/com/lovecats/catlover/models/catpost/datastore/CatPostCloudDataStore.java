package com.lovecats.catlover.models.catpost.datastore;

import com.lovecats.catlover.capsules.common.Config;
import com.lovecats.catlover.models.catpost.api.CatPostApi;
import com.lovecats.catlover.models.catpost.CatPostEntity;

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;

import hugo.weaving.DebugLog;
import retrofit.RestAdapter;
import retrofit.mime.TypedFile;
import rx.Observable;

public class CatPostCloudDataStore implements CatPostDataStore {

    private final CatPostApi catPostApi;

    public CatPostCloudDataStore(CatPostApi catPostApi) {
        this.catPostApi = catPostApi;
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
            e.printStackTrace();
        }

        return catPostEntities;
    }

    @Override
    public Collection<CatPostEntity> getCatPostsForServerIds(HashSet<String> ids) {
        return new HashSet<>();
    }

    @Override
    public Observable<CatPostEntity> getCatPost(String serverId) {
        return null;
    }

    @Override
    public Collection<CatPostEntity> getRandomCatPosts(int howMany) {
        return null;
    }

    public Observable<CatPostEntity> createPost(CatPostEntity catPostEntity) {

        String path = catPostEntity.getImagePath();

        if (path.length() > 0) {
            TypedFile typedFile = new TypedFile("multipart/form-data", new File(catPostEntity.getImagePath()));
            return catPostApi.upload(typedFile, catPostEntity.getCategory(), catPostEntity.getCaption());
        } else {
            return catPostApi.uploadWithUrl(catPostEntity);
        }
    }
}
