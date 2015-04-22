package com.lovecats.catlover.api;

import com.lovecats.catlover.data.AuthModel;
import com.lovecats.catlover.data.LoginWrapper;

import retrofit.Callback;
import retrofit.http.Body;
import retrofit.http.POST;

public interface LoginApi {
    @POST("/sessions.json")
    void getToken(@Body LoginWrapper loginWrapper, Callback<AuthModel> response);
}

