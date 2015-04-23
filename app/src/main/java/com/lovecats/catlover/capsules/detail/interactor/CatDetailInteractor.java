package com.lovecats.catlover.capsules.detail.interactor;

import com.lovecats.catlover.models.comment.CommentEntity;
import com.lovecats.catlover.models.catpost.CatPostEntity;
import com.lovecats.catlover.models.vote.VoteEntity;

import java.util.List;

import rx.Observable;

public interface CatDetailInteractor {

    Observable<CatPostEntity> getPostFromId(String serverId);

    Observable<CommentEntity> sendComment(String comment, String serverId);

    Observable<VoteEntity> sendVote(String serverId, boolean positive);

    Observable<Boolean> isFavorite(String serverId);
}
