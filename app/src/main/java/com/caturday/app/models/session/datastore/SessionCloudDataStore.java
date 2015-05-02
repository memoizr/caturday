package com.caturday.app.models.session.datastore;

import android.util.Base64;

import com.caturday.app.models.session.SessionEntity;
import com.caturday.app.models.session.api.SessionApi;
import com.caturday.app.models.user.UserEntity;

import rx.Observable;

public class SessionCloudDataStore implements SessionDataStore {

    private final SessionApi sessionApi;

    public SessionCloudDataStore(SessionApi sessionApi) {
        this.sessionApi = sessionApi;
    }

    @Override
    public Observable<UserEntity> login(SessionEntity sessionEntity) {
        return sessionApi.login(encodePassword(sessionEntity));
    }

    public Observable<UserEntity> signup(SessionEntity sessionEntity) {
        return sessionApi.signup(encodePassword(sessionEntity));
    }

    private SessionEntity encodePassword(SessionEntity sessionEntity) {

        String password = sessionEntity.getPassword();
        try {
            password = Base64.encodeToString(password.getBytes("UTF8"), Base64.NO_WRAP);
        } catch (Exception e) {
            e.printStackTrace();
        }
        sessionEntity.setPassword(password);
        return sessionEntity;
    }
}
