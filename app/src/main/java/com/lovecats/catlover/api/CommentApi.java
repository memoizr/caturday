package com.lovecats.catlover.api;

import com.lovecats.catlover.data.CommentModel;

import retrofit.Callback;
import retrofit.http.Body;
import retrofit.http.Header;
import retrofit.http.POST;

/**
 * Created by user on 21/03/15.
 */
public interface CommentApi {
    @POST("/comment")
    public void postComment(@Header("Auth-Token") String authToken, @Body CommentModel commentModel, Callback<CommentModel> response);
}
