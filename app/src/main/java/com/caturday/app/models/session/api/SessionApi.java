package com.caturday.app.models.session.api;

import com.caturday.app.models.session.SessionEntity;
import com.caturday.app.models.user.UserEntity;

import retrofit.http.Body;
import retrofit.http.POST;
import rx.Observable;

public interface SessionApi {

    @POST("/sessions/login")
    Observable<UserEntity> login(@Body SessionEntity sessionEntity);

    @POST("/sessions/register")
    Observable<UserEntity> signup(@Body SessionEntity sessionEntity);
}
