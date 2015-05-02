package com.caturday.app.capsules.detail.interactor;

import com.caturday.app.models.catpost.CatPostEntity;
import com.caturday.app.models.vote.VoteEntity;

import rx.Observable;

public interface CatDetailInteractor {

    Observable<CatPostEntity> getPostFromId(String serverId);

    Observable<CatPostEntity> sendComment(String comment, String serverId);

    Observable<VoteEntity> sendVote(String serverId, boolean positive);

    Observable<Boolean> isFavorite(String serverId);

    boolean isUserLoggedIn();
}
