package com.caturday.app.models.vote.api;

import com.caturday.app.models.vote.VoteEntity;

import retrofit.http.Body;
import retrofit.http.POST;
import rx.Observable;

public interface VoteApi {
    @POST("/vote")
    Observable<VoteEntity> postVote(@Body VoteEntity voteEntity);
}
