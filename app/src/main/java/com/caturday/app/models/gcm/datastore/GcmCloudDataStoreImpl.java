package com.caturday.app.models.gcm.datastore;

import com.caturday.app.models.gcm.GcmApi;
import com.caturday.app.models.gcm.GcmRegistrationEntity;

import rx.Observable;

public class GcmCloudDataStoreImpl implements GcmCloudDataStore{

    private final GcmApi api;

    public GcmCloudDataStoreImpl(GcmApi api) {
        this.api = api;
    }

    @Override
    public Observable<GcmRegistrationEntity> registerDevice(GcmRegistrationEntity entity) {
        return api.postGcmRegistration(entity);
    }
}
