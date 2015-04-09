package com.lovecats.catlover.models.catpost.repository;

import com.lovecats.catlover.models.catpost.CatPostEntity;

import java.util.Collection;
import java.util.HashSet;

public interface CatPostRepository {

    Collection<CatPostEntity> getCatPostsForPageAndCategory(int page, String category, boolean fromNetwork);

    Collection<CatPostEntity> getCatPostsForIds(HashSet<String> ids);

    CatPostEntity getCatPost(String serverId);

    Collection<CatPostEntity> getRandomCatPosts(int howMany);
}
