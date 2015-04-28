package com.lovecats.catlover.models.session.db;

import com.google.gson.Gson;
import com.lovecats.catlover.models.session.SessionEntity;

import greendao.DaoSession;
import greendao.Session;
import greendao.SessionDao;
import rx.Observable;

/**
 * Created by Cat#2 on 28/04/15.
 */
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

    public boolean sessionLoggedIn() {
        return getSessionDao().count() > 0;
    }

    private SessionDao getSessionDao() {
        return daoSession.getSessionDao();
    }
}
