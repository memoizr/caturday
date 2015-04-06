package com.lovecats.catlover.capsules.detail.interactor;

import com.lovecats.catlover.models.comment.CommentEntity;
import com.lovecats.catlover.models.catpost.CatPostEntity;
import com.lovecats.catlover.models.vote.VoteEntity;
import com.lovecats.catlover.util.concurrent.WorkerCallback;

import rx.Observable;

public interface CatDetailInteractor {

    void getPostFromId(String serverId, WorkerCallback<CatPostEntity> callback);

    Observable<CommentEntity> sendComment(String comment, String serverId);

    Observable<VoteEntity> sendVote(String serverId);
}
