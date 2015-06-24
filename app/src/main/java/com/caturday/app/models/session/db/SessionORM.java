package com.caturday.app.models.session.db;

import com.caturday.app.models.session.SessionEntity;

import java.util.List;

import greendao.DaoSession;
import greendao.Session;
import greendao.SessionDao;
import rx.Observable;

public class SessionORM {

    private DaoSession daoSession;


    public SessionORM(DaoSession daoSession) {

        this.daoSession = daoSession;
    }

    public Observable<SessionEntity> performLogin(SessionEntity sessionEntity) {

        performLogout();

        Session session = new Session();
        session.setAuthToken(sessionEntity.getAuthToken());
        getSessionDao().insertOrReplace(session);

        return Observable.just(sessionEntity);
    }

    public void performLogout() {
        getSessionDao().deleteAll();
    }

    public boolean sessionAvailable() {
        return getSessionDao().count() > 0;
    }

    private SessionDao getSessionDao() {
        return daoSession.getSessionDao();
    }

    public SessionEntity currentSession() {

        List<Session> sessions = getSessionDao().loadAll();
        SessionEntity sessionEntity = new SessionEntity();
        if (sessions.size() > 0) {
            sessionEntity.setAuthToken(sessions.get(0).getAuthToken());
        }

        return sessionEntity;
    }
}
