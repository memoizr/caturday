package com.lovecats.catlover.ui.stream.data.datastore;

import com.lovecats.catlover.ui.stream.data.CatPostEntity;

import java.util.Collection;

import greendao.CatPost;

/**
 * Created by user on 02/04/15.
 */
public interface CatPostDataStore {

    Collection<CatPostEntity> getCatPostsForPageAndCategory(int page, String category);

    CatPostEntity getCatPost(String serverId);
}
