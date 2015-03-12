package com.lovecats.catlover.data;

import retrofit.Callback;
import retrofit.http.Body;
import retrofit.http.POST;

public interface LoginApi {
    @POST("/sessions.json")
    public void getToken(@Body LoginWrapper loginWrapper, Callback<AuthModel> response);
}

