package com.lovecats.catlover.ui.stream.data.db;

import com.lovecats.catlover.ui.stream.data.CatPostEntity;

import java.util.Collection;

public interface CatPostDb {

    CatPostEntity getCatPostForServerId(String id);

    Collection<CatPostEntity> getPostsForPageAndCategory(int page, String category);

    Collection<CatPostEntity> getCatPostsForServerIds(Collection<String> catPostServerIds);

    CatPostEntity getRandomCatPost();

    long getCount();

    void createPost(CatPostEntity catPostEntity);

    void createMultiplePost(Collection<CatPostEntity> catPostEntity);
}
