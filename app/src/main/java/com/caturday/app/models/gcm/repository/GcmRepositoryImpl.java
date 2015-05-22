package com.caturday.app.models.gcm.repository;

import com.caturday.app.models.gcm.GcmRegistrationEntity;
import com.caturday.app.models.gcm.datastore.GcmCloudDataStore;

import rx.Observable;

public class GcmRepositoryImpl implements GcmRepository {

    private final GcmCloudDataStore cloudDataStore;

    public GcmRepositoryImpl(GcmCloudDataStore cloudDataStore) {
        this.cloudDataStore = cloudDataStore;
    }
    @Override
    public Observable<GcmRegistrationEntity> registerDevice(String regId) {
        GcmRegistrationEntity entity = new GcmRegistrationEntity();
        entity.setRegistrationId(regId);
        return cloudDataStore.registerDevice(entity);
    }
}
