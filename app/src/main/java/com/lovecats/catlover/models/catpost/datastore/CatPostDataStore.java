package com.lovecats.catlover.models.catpost.datastore;

import com.lovecats.catlover.models.catpost.CatPostEntity;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;

/**
 * Created by user on 02/04/15.
 */
public interface CatPostDataStore {

    Collection<CatPostEntity> getCatPostsForPageAndCategory(int page, String category);

    Collection<CatPostEntity> getCatPostsForServerIds(HashSet<String> ids);

    CatPostEntity getCatPost(String serverId);

    Collection<CatPostEntity> getRandomCatPosts(int howMany);
    
}
