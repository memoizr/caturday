package com.lovecats.catlover.models.user.api;

import com.lovecats.catlover.models.user.UserEntity;

import retrofit.http.Body;
import retrofit.http.DELETE;
import retrofit.http.GET;
import retrofit.http.POST;
import retrofit.http.PUT;
import retrofit.http.Path;
import rx.Observable;

public interface UserApi {
    @GET("/user/{id}")
    Observable<UserEntity> getUserForId(@Path("id") String serverId);

    @PUT("/user")
    Observable<UserEntity> updateUser(UserEntity userEntity);

    @POST("/follow")
    Observable<UserEntity> followUser(@Body String serverId);

    @DELETE("/follow")
    Observable<UserEntity> unfollowUser(@Body String serverId);
}
