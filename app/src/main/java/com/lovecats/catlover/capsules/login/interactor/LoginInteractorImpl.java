package com.lovecats.catlover.capsules.login.interactor;


import com.lovecats.catlover.models.user.UserEntity;
import com.lovecats.catlover.models.user.repository.UserRepository;

import rx.Observable;

public class LoginInteractorImpl implements LoginInteractor {

    private final UserRepository userRepository;

    public LoginInteractorImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public Observable<UserEntity> performLogin(String email, String password) {
        return userRepository.login(email, password);
    }

    @Override
    public Observable<UserEntity> performSignup(String username, String email, String password) {
        return userRepository.signup(username, email, password);
    }

    @Override
    public Observable<UserEntity> saveUser(UserEntity userEntity) {
        return userRepository.saveUser(userEntity);
    }
}
