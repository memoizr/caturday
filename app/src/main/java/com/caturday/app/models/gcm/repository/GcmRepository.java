package com.caturday.app.models.gcm.repository;

import com.caturday.app.models.gcm.GcmRegistrationEntity;

import rx.Observable;

public interface GcmRepository {
    Observable<GcmRegistrationEntity> registerDevice(String regId);
}
