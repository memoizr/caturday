package com.lovecats.catlover.capsules.detail.interactor;

import com.lovecats.catlover.models.catpost.CatPostEntity;
import com.lovecats.catlover.models.vote.VoteEntity;

import rx.Observable;

public interface CatDetailInteractor {

    Observable<CatPostEntity> getPostFromId(String serverId);

    Observable<CatPostEntity> sendComment(String comment, String serverId);

    Observable<VoteEntity> sendVote(String serverId, boolean positive);

    Observable<Boolean> isFavorite(String serverId);

    Observable<CatPostEntity> updateCatPost(CatPostEntity catPostEntity);
}
