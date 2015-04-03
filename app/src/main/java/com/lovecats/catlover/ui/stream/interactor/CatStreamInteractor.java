package com.lovecats.catlover.ui.stream.interactor;

import java.util.Collection;

import greendao.CatPost;
import retrofit.Callback;

/**
 * Created by user on 02/04/15.
 */
public interface CatStreamInteractor {
    public void getCatPostPageAndType(int page,
                                      String streamType,
                                      Callback<Collection<CatPost>> callback);
}