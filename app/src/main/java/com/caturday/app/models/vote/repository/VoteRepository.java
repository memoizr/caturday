package com.caturday.app.models.vote.repository;

import com.caturday.app.models.vote.VoteEntity;

import rx.Observable;

/**
 * Created by Cat#2 on 06/04/15.
 */
public interface VoteRepository {

    Observable<VoteEntity> sendVote(VoteEntity voteEntity);
}
