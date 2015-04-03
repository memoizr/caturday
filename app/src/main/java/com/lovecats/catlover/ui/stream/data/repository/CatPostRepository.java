package com.lovecats.catlover.ui.stream.data.repository;

import java.util.Collection;

import greendao.CatPost;

public interface CatPostRepository {

    public Collection<CatPost> getCatPostsForPageAndCategory(int page, String category);

    public CatPost getCatPost(String serverId);
}
