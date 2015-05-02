package com.caturday.app.models.catpost.repository;

import com.caturday.app.models.catpost.CatPostEntity;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;

import rx.Observable;

public interface CatPostRepository {

    Observable<List<CatPostEntity>> getCatPostsForPageAndCategory(int page, String category, boolean fromNetwork);

    Observable<Collection<CatPostEntity>> getCatPostsForIds(HashSet<String> ids);

    Observable<CatPostEntity> getCatPost(String serverId);

    Collection<CatPostEntity> getRandomCatPosts(int howMany);

    Observable<CatPostEntity> createPost(CatPostEntity catPostEntity);

    void eraseCache();
}
