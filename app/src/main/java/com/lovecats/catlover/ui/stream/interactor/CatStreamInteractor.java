package com.lovecats.catlover.ui.stream.interactor;

import java.util.Collection;

import greendao.CatPost;

/**
 * Created by user on 02/04/15.
 */
public interface CatStreamInteractor {
    public Collection<CatPost> getCatPostPageAndType(int page, String streamType);
}
