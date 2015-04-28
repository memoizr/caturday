package com.lovecats.catlover.capsules.login.interactor;


import com.lovecats.catlover.models.session.SessionEntity;
import com.lovecats.catlover.models.session.repository.SessionRepository;
import com.lovecats.catlover.models.user.UserEntity;

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

//    @Override
//    public Observable<UserEntity> saveUser(UserEntity userEntity) {
//        return sessionRepository.saveUser(userEntity);
//    }
}
