package com.lovecats.catlover.ui.stream.data.repository;

import java.util.Collection;

import greendao.CatPost;

/**
 * Created by user on 02/04/15.
 */
public interface CatPostRepository {

    public Collection<CatPost> getCatPostsForPageAndCategory(int page, String category);

    public CatPost getCatPost(String serverId);
}
