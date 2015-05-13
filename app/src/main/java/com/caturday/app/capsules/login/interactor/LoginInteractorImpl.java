package com.caturday.app.capsules.login.interactor;


import com.caturday.app.models.session.SessionEntity;
import com.caturday.app.models.session.repository.SessionRepository;
import com.caturday.app.models.user.UserEntity;

import rx.Observable;

public class LoginInteractorImpl implements LoginInteractor {

    private final SessionRepository sessionRepository;

    public LoginInteractorImpl(SessionRepository sessionRepository) {
        this.sessionRepository = sessionRepository;
    }

    @Override
    public Observable<UserEntity> performLogin(String email, String password) {
        SessionEntity sessionEntity = new SessionEntity();
        sessionEntity.setEmail(email);
        sessionEntity.setPassword(password);
        return sessionRepository.login(sessionEntity);
    }

    @Override
    public Observable<UserEntity> performSignup(String username, String email, String password) {
        SessionEntity sessionEntity = new SessionEntity();
        sessionEntity.setEmail(email);
        sessionEntity.setPassword(password);
        sessionEntity.setUsername(username);
        return sessionRepository.signup(sessionEntity);
    }
}
