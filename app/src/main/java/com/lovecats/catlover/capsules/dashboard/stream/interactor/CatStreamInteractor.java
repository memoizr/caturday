package com.lovecats.catlover.capsules.dashboard.stream.interactor;

import com.lovecats.catlover.models.catpost.CatPostEntity;

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
}
