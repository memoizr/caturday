package com.lovecats.catlover.data;

import com.lovecats.catlover.common.api.BaseRequest;
import com.lovecats.catlover.ui.detail.api.CommentApi;
import com.lovecats.catlover.ui.detail.data.CommentEntity;

import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * Created by user on 21/03/15.
 */
public class CommentPoster extends BaseRequest {

//    public static void postComment(String authToken, final CommentEntity commentModel,
//                                   final Callback<CommentEntity> callback) {
//
//
//        RestAdapter restAdapter = getRestAdapter();
//        restAdapter.setLogLevel(RestAdapter.LogLevel.FULL);
//        CommentApi api = restAdapter.create(CommentApi.class);
//
//        api.postComment(authToken, commentModel, new Callback<CommentEntity>() {
//            @Override
//            public void success(CommentEntity commentEntity, Response response) {
//                callback.success(commentEntity, response);
//            }
//
//            @Override
//            public void failure(RetrofitError error) {
//                error.printStackTrace();
//            }
//        });
//    }
}
