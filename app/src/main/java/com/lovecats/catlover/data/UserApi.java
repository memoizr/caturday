package com.lovecats.catlover.data;

import retrofit.Callback;
import retrofit.http.Header;
import retrofit.http.POST;

public interface UserApi {
    @POST("/user_data.json")
    public void getUser(@Header("Auth-Token") String authToken, Callback<UserFetcher> response);
}
