package com.lovecats.catlover.capsules.detail.api;

import com.lovecats.catlover.models.vote.VoteEntity;

import retrofit.http.Body;
import retrofit.http.POST;
import rx.Observable;

public interface VoteApi {
    @POST("/vote")
    Observable<VoteEntity> postVote(@Body VoteEntity voteEntity);
}
