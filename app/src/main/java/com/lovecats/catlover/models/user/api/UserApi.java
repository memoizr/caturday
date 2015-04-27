package com.lovecats.catlover.models.user.api;

import com.lovecats.catlover.models.user.LoginEntity;
import com.lovecats.catlover.models.user.UserEntity;

import retrofit.http.Body;
import retrofit.http.GET;
import retrofit.http.POST;
import retrofit.http.PUT;
import retrofit.http.Path;
import rx.Observable;

public interface UserApi {

    @POST("/sessions/login")
    Observable<UserEntity> login(@Body LoginEntity loginEntity);

    @POST("/sessions/register")
    Observable<UserEntity> signup(@Body LoginEntity loginEntity);

    @GET("/user/{id}")
    Observable<UserEntity> getUserForId(@Path("id") String serverId);

    @PUT("/user")
    Observable<UserEntity> updateUser(UserEntity userEntity);
}
