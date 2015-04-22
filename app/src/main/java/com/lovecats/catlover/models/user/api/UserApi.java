package com.lovecats.catlover.models.user.api;

import com.lovecats.catlover.models.user.LoginEntity;
import com.lovecats.catlover.models.user.UserEntity;

import retrofit.http.Body;
import retrofit.http.POST;
import rx.Observable;

public interface UserApi {

    @POST("/sessions/login")
    Observable<UserEntity> login(@Body LoginEntity loginEntity);
}
