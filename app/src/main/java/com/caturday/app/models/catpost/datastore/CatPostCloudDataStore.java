package com.caturday.app.models.catpost.datastore;

import com.caturday.app.capsules.common.Config;
import com.caturday.app.models.catpost.api.CatPostApi;
import com.caturday.app.models.catpost.CatPostEntity;

import java.io.File;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;

import retrofit.RestAdapter;
import retrofit.mime.TypedFile;
import rx.Observable;

public class CatPostCloudDataStore implements CatPostDataStore {

    private final CatPostApi catPostApi;

    public CatPostCloudDataStore(CatPostApi catPostApi) {
        this.catPostApi = catPostApi;
    }

    @Override
    public Observable<List<CatPostEntity>> getCatPostsForPageAndCategory(int page, String category) {
        String endpoint = Config.getEndpoint();

        RestAdapter adapter = new RestAdapter.Builder()
                .setEndpoint(endpoint)
                .build();

        final CatPostApi api = adapter.create(CatPostApi.class);
        Observable<List<CatPostEntity>> catPostEntities;
        catPostEntities = api.getPostsForPageAndCategory(page, category, null);

        return catPostEntities;
    }

    @Override
    public Observable<List<CatPostEntity>> getCatPostsForPageAndUserId(int page, String userId) {
        String endpoint = Config.getEndpoint();

        RestAdapter adapter = new RestAdapter.Builder()
                .setEndpoint(endpoint)
                .build();

        final CatPostApi api = adapter.create(CatPostApi.class);
        Observable<List<CatPostEntity>> catPostEntities;
        catPostEntities = api.getPostsForPageAndCategory(page, null, userId);

        return catPostEntities;
    }

    @Override
    public Collection<CatPostEntity> getCatPostsForServerIds(HashSet<String> ids) {
        return new HashSet<>();
    }

    @Override
    public Observable<CatPostEntity> getCatPost(String serverId) {
        return catPostApi.getPostForId(serverId);
    }

    @Override
    public Collection<CatPostEntity> getRandomCatPosts(int howMany) {
        return null;
    }

    @Override
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
