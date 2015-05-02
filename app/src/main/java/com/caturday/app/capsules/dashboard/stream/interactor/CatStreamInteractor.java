package com.caturday.app.capsules.dashboard.stream.interactor;

import com.caturday.app.models.catpost.CatPostEntity;

import java.util.Collection;
import java.util.List;

import retrofit.Callback;
import rx.Observable;

public interface CatStreamInteractor {
    Observable<List<CatPostEntity>> getCatPostPageAndType(int page,
                               String streamType,
                               boolean fromNetwork);

    void eraseCache();
}
