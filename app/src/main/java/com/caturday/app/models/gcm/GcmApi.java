package com.caturday.app.models.gcm;

import retrofit.http.Body;
import retrofit.http.POST;
import rx.Observable;

public interface GcmApi {

    @POST("/gcm_registration")
    Observable<GcmRegistrationEntity> postGcmRegistration(@Body GcmRegistrationEntity gcmRegistrationEntity);
}
