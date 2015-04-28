package com.lovecats.catlover.models.session.datastore;

import com.lovecats.catlover.models.session.SessionEntity;
import com.lovecats.catlover.models.session.db.SessionORM;

import rx.Observable;

public class SessionLocalDataStore {


    private SessionORM sessionORM;

    public SessionLocalDataStore(SessionORM sessionORM) {

        this.sessionORM = sessionORM;
    }

    public Observable<SessionEntity> login(SessionEntity sessionEntity) {

        return sessionORM.performLogin(sessionEntity);
    }

    public void logout() {

        sessionORM.performLogout();
    }
}
