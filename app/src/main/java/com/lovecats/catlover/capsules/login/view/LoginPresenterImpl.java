package com.lovecats.catlover.capsules.login.view;


import com.lovecats.catlover.capsules.login.interactor.LoginInteractor;

import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class LoginPresenterImpl implements LoginPresenter {

    private final LoginView loginView;
    private final LoginInteractor loginInteractor;

    public LoginPresenterImpl(LoginView loginView, LoginInteractor loginInteractor) {
        this.loginView = loginView;
        this.loginInteractor = loginInteractor;
    }

    @Override
    public void performLogin(String email, String password) {
        loginInteractor.performLogin(email, password)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(userEntity -> {
                            System.out.println(userEntity);
                            loginInteractor.saveUser(userEntity);
                            loginView.successAnimation();
                        },
                        e -> {
                            loginView.failureAnimation();
                            e.printStackTrace();
                        });
    }

    @Override
    public void performSignup(String username, String email, String password) {
        loginInteractor.performSignup(username, email, password);
    }
}
