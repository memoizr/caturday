package com.lovecats.catlover.models.comment.api;

import com.lovecats.catlover.models.catpost.CatPostEntity;
import com.lovecats.catlover.models.comment.CommentEntity;

import retrofit.http.Body;
import retrofit.http.POST;
import rx.Observable;

public interface CommentApi {

    @POST("/comment")
    Observable<CatPostEntity> postComment(@Body CommentEntity commentModel);
}
