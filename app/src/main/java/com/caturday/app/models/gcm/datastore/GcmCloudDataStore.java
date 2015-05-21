package com.caturday.app.models.gcm.datastore;

import com.caturday.app.models.gcm.GcmRegistrationEntity;

import rx.Observable;

public interface GcmCloudDataStore {
    Observable<GcmRegistrationEntity> registerDevice(GcmRegistrationEntity entity);
}
