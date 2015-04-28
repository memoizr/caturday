package com.lovecats.catlover.models.session.datastore;

import com.lovecats.catlover.models.session.SessionEntity;
import com.lovecats.catlover.models.user.UserEntity;

import rx.Observable;

public interface SessionDataStore {

    Observable<UserEntity> login(SessionEntity sessionEntity);
}
