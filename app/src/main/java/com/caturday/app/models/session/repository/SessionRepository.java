package com.caturday.app.models.session.repository;

import com.caturday.app.models.session.SessionEntity;
import com.caturday.app.models.user.UserEntity;

import rx.Observable;

public interface SessionRepository {

    Observable<UserEntity> login(SessionEntity sessionEntity);

    Observable<UserEntity> signup(SessionEntity sessionEntity);

    boolean sessionAvailable();

    SessionEntity currentSession();

    void logout(String authToken);
}
