package com.lovecats.catlover.capsules.newpost.interactor;


import com.lovecats.catlover.models.catpost.CatPostEntity;

import rx.Observable;

public interface NewPostInteractor {

    Observable<CatPostEntity> createPost(String filename);
}
