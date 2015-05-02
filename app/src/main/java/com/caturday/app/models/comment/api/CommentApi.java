package com.caturday.app.models.comment.api;

import com.caturday.app.models.catpost.CatPostEntity;
import com.caturday.app.models.comment.CommentEntity;

import retrofit.http.Body;
import retrofit.http.POST;
import rx.Observable;

public interface CommentApi {

    @POST("/comment")
    Observable<CatPostEntity> postComment(@Body CommentEntity commentModel);
}
