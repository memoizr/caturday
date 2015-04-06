package com.lovecats.catlover.capsules.detail.api;

import com.lovecats.catlover.models.comment.CommentEntity;

import retrofit.http.Body;
import retrofit.http.POST;
import rx.Observable;

/**
 * Created by user on 21/03/15.
 */
public interface CommentApi {
    @POST("/comment")
    Observable<CommentEntity> postComment(@Body CommentEntity commentModel);
}
