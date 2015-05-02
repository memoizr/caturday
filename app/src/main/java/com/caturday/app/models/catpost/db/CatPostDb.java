package com.caturday.app.models.catpost.db;

import com.caturday.app.models.catpost.CatPostEntity;

import java.util.Collection;
import java.util.HashSet;

import rx.Observable;

public interface CatPostDb {

    Observable<CatPostEntity> getCatPostForServerId(String id);

    Collection<CatPostEntity> getPostsForPageAndCategory(int page, String category);

    Collection<CatPostEntity> getCatPostsForServerIds(HashSet<String> catPostServerIds);

    CatPostEntity getRandomCatPost();

    Collection<CatPostEntity> getRandomCatPosts(int howMany);

    long getCount();

    void createPost(CatPostEntity catPostEntity);

    void createMultiplePost(Collection<CatPostEntity> catPostEntity);

    void eraseCache();

    CatPostEntity updateCatPost(CatPostEntity catPostEntity);
}
