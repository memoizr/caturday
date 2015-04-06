package com.lovecats.catlover.models.catpost.repository;

import com.lovecats.catlover.models.catpost.CatPostEntity;

import java.util.Collection;

public interface CatPostRepository {

    Collection<CatPostEntity> getCatPostsForPageAndCategory(int page, String category);
    CatPostEntity getCatPost(String serverId);
}
