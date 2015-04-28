package com.lovecats.catlover.models.session.datastore;

import com.lovecats.catlover.models.session.SessionEntity;
import com.lovecats.catlover.models.session.api.SessionApi;
import com.lovecats.catlover.models.user.UserEntity;

import rx.Observable;

public class SessionCloudDataStore implements SessionDataStore {

    private final SessionApi sessionApi;

    public SessionCloudDataStore(SessionApi sessionApi) {
        this.sessionApi = sessionApi;
    }

    @Override
    public Observable<UserEntity> login(SessionEntity sessionEntity) {
        return sessionApi.login(sessionEntity);
    }

    public Observable<UserEntity> signup(SessionEntity sessionEntity) {
        return sessionApi.signup(sessionEntity);
    }
}
