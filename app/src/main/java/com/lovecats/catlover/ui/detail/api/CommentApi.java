package com.lovecats.catlover.ui.detail.api;

import com.lovecats.catlover.data.CommentModel;
import com.lovecats.catlover.ui.detail.data.CommentEntity;

import retrofit.Callback;
import retrofit.http.Body;
import retrofit.http.Header;
import retrofit.http.POST;

/**
 * Created by user on 21/03/15.
 */
public interface CommentApi {
    @POST("/comment")
    void postComment(@Header("Auth-Token") String authToken,
                            @Body CommentEntity commentModel,
                            Callback<CommentEntity> response);
}
