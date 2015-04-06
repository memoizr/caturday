package com.lovecats.catlover.models.catpost.datastore;

import com.lovecats.catlover.models.catpost.CatPostEntity;

import java.util.Collection;

/**
 * Created by user on 02/04/15.
 */
public interface CatPostDataStore {

    Collection<CatPostEntity> getCatPostsForPageAndCategory(int page, String category);

    CatPostEntity getCatPost(String serverId);
}
