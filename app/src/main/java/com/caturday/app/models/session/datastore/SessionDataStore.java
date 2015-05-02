package com.caturday.app.models.session.datastore;

import com.caturday.app.models.session.SessionEntity;
import com.caturday.app.models.user.UserEntity;

import rx.Observable;

public interface SessionDataStore {

    Observable<UserEntity> login(SessionEntity sessionEntity);
}
