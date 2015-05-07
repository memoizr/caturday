package com.caturday.app.models.catpost.datastore;

import com.caturday.app.models.catpost.CatPostEntity;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;

import rx.Observable;

public interface CatPostDataStore {

    Observable<List<CatPostEntity>> getCatPostsForPageAndCategory(int page, String category);

    Observable<List<CatPostEntity>> getCatPostsForPageAndUserId(int page, String userId);

    Collection<CatPostEntity> getCatPostsForServerIds(HashSet<String> ids);

    Observable<CatPostEntity> getCatPost(String serverId);

    Collection<CatPostEntity> getRandomCatPosts(int howMany);
    
}
