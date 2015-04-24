package com.lovecats.catlover.models.catpost.db;

import com.lovecats.catlover.models.catpost.CatPostEntity;

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

    Observable<CatPostEntity> updateCatPost(CatPostEntity catPostEntity);
}
