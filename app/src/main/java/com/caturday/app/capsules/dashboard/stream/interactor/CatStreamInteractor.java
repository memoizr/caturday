package com.caturday.app.capsules.dashboard.stream.interactor;

import com.caturday.app.models.catpost.CatPostEntity;

import java.util.List;

import rx.Observable;

public interface CatStreamInteractor {

    Observable<List<CatPostEntity>> getCatPostPageAndType(int page,
                               String streamType,
                               boolean fromNetwork);

    void eraseCache();

    Observable<CatPostEntity> getCatPost(String serverId);

    Observable<CatPostEntity> catPostVoted(String serverId);

    boolean userLoggedIn();
}
