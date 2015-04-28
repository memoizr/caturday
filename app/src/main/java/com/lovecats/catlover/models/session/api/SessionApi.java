package com.lovecats.catlover.models.session.api;

import com.lovecats.catlover.models.session.SessionEntity;
import com.lovecats.catlover.models.user.UserEntity;

import retrofit.http.Body;
import retrofit.http.POST;
import rx.Observable;

public interface SessionApi {

    @POST("/sessions/login")
    Observable<UserEntity> login(@Body SessionEntity sessionEntity);

    @POST("/sessions/register")
    Observable<UserEntity> signup(@Body SessionEntity sessionEntity);
}
