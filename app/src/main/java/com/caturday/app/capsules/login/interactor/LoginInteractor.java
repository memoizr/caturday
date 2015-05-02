package com.caturday.app.capsules.login.interactor;


import com.caturday.app.models.user.UserEntity;

import rx.Observable;

public interface LoginInteractor {

    Observable<UserEntity> performLogin(String email, String password);

    Observable<UserEntity> performSignup(String username, String email, String password);

//    Observable<UserEntity> saveUser(UserEntity userEntity);
}
