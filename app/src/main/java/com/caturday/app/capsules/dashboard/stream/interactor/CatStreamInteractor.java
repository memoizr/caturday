package com.caturday.app.capsules.dashboard.stream.interactor;

import com.caturday.app.models.catpost.CatPostEntity;

import java.util.Collection;

import retrofit.Callback;

/**
 * Created by user on 02/04/15.
 */
public interface CatStreamInteractor {
    void getCatPostPageAndType(int page,
                               String streamType,
                               boolean fromNetwork,
                               Callback<Collection<CatPostEntity>> callback);

    void eraseCache();
}
