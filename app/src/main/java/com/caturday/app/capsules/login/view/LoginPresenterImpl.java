package com.caturday.app.capsules.login.view;


import android.os.Handler;

import com.caturday.app.capsules.login.interactor.LoginInteractor;
import com.caturday.app.util.helper.RetrofitErrorHelper;

import retrofit.RetrofitError;
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
                            loginView.successAnimation();
                        },
                        this::handleError);
    }

    private void handleError(Throwable error) {
        if (error instanceof RetrofitError) {

            loginView.failureAnimation();
            new Handler().postDelayed(() -> {

                RetrofitErrorHelper.formatErrorMessageRx((RetrofitError) error)
                        .subscribe(s ->
                                loginView.toggleError(true, s));
            }, 1200);
        }
        error.printStackTrace();
    }

    @Override
    public void performSignup(String username, String email, String password) {
        loginInteractor.performSignup(username, email, password)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(userEntity -> {
                            loginView.successAnimation();
                        },
                        e -> {
                            loginView.failureAnimation();
                            handleError(e);
                        });
    }
}
