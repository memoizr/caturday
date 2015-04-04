package com.lovecats.catlover.ui.stream.data.repository;

import com.lovecats.catlover.ui.stream.data.CatPostEntity;

import java.util.Collection;

public interface CatPostRepository {

    Collection<CatPostEntity> getCatPostsForPageAndCategory(int page, String category);
    CatPostEntity getCatPost(String serverId);
}
