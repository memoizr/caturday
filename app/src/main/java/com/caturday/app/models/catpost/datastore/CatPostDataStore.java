package com.caturday.app.models.catpost.datastore;

import com.caturday.app.models.catpost.CatPostEntity;

import java.util.Collection;
import java.util.HashSet;

import rx.Observable;

public interface CatPostDataStore {

    Collection<CatPostEntity> getCatPostsForPageAndCategory(int page, String category);

    Collection<CatPostEntity> getCatPostsForServerIds(HashSet<String> ids);

    Observable<CatPostEntity> getCatPost(String serverId);

    Collection<CatPostEntity> getRandomCatPosts(int howMany);
    
}
