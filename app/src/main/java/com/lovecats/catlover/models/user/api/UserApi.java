package com.lovecats.catlover.models.user.api;

import com.lovecats.catlover.models.user.UserEntity;

import retrofit.http.DELETE;
import retrofit.http.Field;
import retrofit.http.FormUrlEncoded;
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

    @FormUrlEncoded
    @POST("/follow")
    Observable<UserEntity> followUser(@Field("followable_id") String serverId);
    @FormUrlEncoded
    @DELETE("/follow")
    Observable<UserEntity> unfollowUser(@Field("followable_id") String serverId);
}
