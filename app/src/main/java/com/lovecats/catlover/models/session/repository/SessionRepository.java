package com.lovecats.catlover.models.session.repository;

import com.lovecats.catlover.models.session.SessionEntity;
import com.lovecats.catlover.models.user.UserEntity;

import rx.Observable;

public interface SessionRepository {

    Observable<UserEntity> login(SessionEntity sessionEntity);

    Observable<UserEntity> signup(SessionEntity sessionEntity);

    boolean sessionAvailable();

    SessionEntity currentSession();

    void logout(String authToken);
}
