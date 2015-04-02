package com.lovecats.catlover.data;

import com.lovecats.catlover.common.api.BaseRequest;
import com.lovecats.catlover.api.CommentApi;

import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * Created by user on 21/03/15.
 */
public class CommentPoster extends BaseRequest {

    public static void postComment(String authToken, final CommentModel commentModel, final Callback<CommentModel> callback) {


        RestAdapter restAdapter = getRestAdapter();
        restAdapter.setLogLevel(RestAdapter.LogLevel.FULL);
        CommentApi api = restAdapter.create(CommentApi.class);

        api.postComment(authToken, commentModel, new Callback<CommentModel>() {
            @Override
            public void success(CommentModel commentModel, Response response) {
                callback.success(commentModel, response);
            }

            @Override
            public void failure(RetrofitError error) {
                error.printStackTrace();
            }
        });
    }
}
