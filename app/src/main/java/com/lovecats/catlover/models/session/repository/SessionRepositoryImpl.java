package com.lovecats.catlover.models.session.repository;

import com.lovecats.catlover.models.session.SessionEntity;
import com.lovecats.catlover.models.session.datastore.SessionCloudDataStore;
import com.lovecats.catlover.models.session.datastore.SessionLocalDataStore;
import com.lovecats.catlover.models.user.UserEntity;
import com.lovecats.catlover.models.user.db.UserORM;

import rx.Observable;

public class SessionRepositoryImpl implements SessionRepository {

    private final SessionCloudDataStore cloudDataStore;
    private final SessionLocalDataStore localDataStore;
    private final UserORM userORM;

    public SessionRepositoryImpl(SessionLocalDataStore localDataStore,
                                 SessionCloudDataStore cloudDataStore,
                                 UserORM userORM) {

        this.localDataStore = localDataStore;
        this.cloudDataStore = cloudDataStore;
        this.userORM = userORM;
    }

    @Override
    public Observable<UserEntity> login(SessionEntity sessionEntity) {
        return cloudDataStore.login(sessionEntity)
                .doOnNext(user -> {
                    SessionEntity entity = new SessionEntity();
                    entity.setAuthToken(user.getAuthToken());
                    localDataStore.login(entity);
                    userORM.logInUser(user);
                });
    }

    @Override
    public Observable<UserEntity> signup(SessionEntity sessionEntity) {
        return cloudDataStore.signup(sessionEntity)
                .doOnNext(user -> {
                    SessionEntity entity = new SessionEntity();
                    entity.setAuthToken(user.getAuthToken());
                    localDataStore.login(entity);
                    userORM.logInUser(user);
                });
    }

    @Override
    public boolean sessionAvailable() {
        return localDataStore.sessionAvailable();
    }

    @Override
    public SessionEntity currentSession() {
        return localDataStore.currentSession();
    }

    @Override
    public void logout(String authToken) {
        localDataStore.logout();
        userORM.performLogout();
    }
}
