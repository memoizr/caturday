package com.lovecats.catlover.ui.stream.data.db;

import com.lovecats.catlover.common.data.TransactionListener;
import com.lovecats.catlover.ui.stream.data.CatPostEntity;

import java.util.Collection;

import greendao.CatPost;

/**
 * Created by user on 02/04/15.
 */
public interface CatPostDb {

    public CatPost getCatPostForServerId(String id);

    public Collection<CatPost> getPostsForPageAndCategory(int page, String category);

    public Collection<CatPost> getCatPostsForServerIds(Collection<String> catPostServerIds);

    public CatPost getRandomCatPost();

    public long getCount();

    public void createPost(CatPostEntity catPostEntity);

    public void createMultiplePost(Collection<CatPostEntity> catPostEntity);
}
