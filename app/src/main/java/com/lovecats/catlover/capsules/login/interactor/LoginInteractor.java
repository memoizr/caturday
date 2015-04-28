package com.lovecats.catlover.capsules.login.interactor;


import com.lovecats.catlover.models.user.UserEntity;

import rx.Observable;

public interface LoginInteractor {

    Observable<UserEntity> performLogin(String email, String password);

    Observable<UserEntity> performSignup(String username, String email, String password);

//    Observable<UserEntity> saveUser(UserEntity userEntity);
}
