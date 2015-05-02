package com.caturday.app.models.session.datastore;

import com.caturday.app.models.session.SessionEntity;
import com.caturday.app.models.session.db.SessionORM;

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

    public boolean sessionAvailable() {

        return sessionORM.sessionAvailable();
    }

    public SessionEntity currentSession() {

        return sessionORM.currentSession();
    }
}
