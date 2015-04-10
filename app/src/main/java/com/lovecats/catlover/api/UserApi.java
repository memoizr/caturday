package com.lovecats.catlover.api;

import com.lovecats.catlover.data.user.UserFetcher;

import retrofit.Callback;
import retrofit.http.Header;
import retrofit.http.POST;

public interface UserApi {
    @POST("/user_data.json")
    void getUser(@Header("Auth-Token") String authToken, Callback<UserFetcher> response);
}
