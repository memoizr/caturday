package com.caturday.app.capsules.newpost.interactor;


import com.caturday.app.models.catpost.CatPostEntity;

import rx.Observable;

public interface NewPostInteractor {

    Observable<CatPostEntity> createPost(CatPostEntity catPostEntity);
}
