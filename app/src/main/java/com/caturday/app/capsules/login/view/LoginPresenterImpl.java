package com.caturday.app.capsules.login.view;


import android.os.Handler;

import com.caturday.app.capsules.common.events.OnLoginSuccessful;
import com.caturday.app.capsules.common.events.StreamRefreshedEvent;
import com.caturday.app.capsules.login.interactor.LoginInteractor;
import com.caturday.app.util.helper.RetrofitErrorHelper;
import com.squareup.otto.Bus;

import retrofit.RetrofitError;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class LoginPresenterImpl implements LoginPresenter {

    private final LoginView loginView;
    private final LoginInteractor loginInteractor;
    private final Bus bus;

    public LoginPresenterImpl(LoginView loginView, LoginInteractor loginInteractor, Bus bus) {
        this.loginView = loginView;
        this.loginInteractor = loginInteractor;
        this.bus = bus;
    }

    @Override
    public void performLogin(String email, String password) {
        loginInteractor.performLogin(email, password)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(userEntity -> {
                            loginView.successAnimation();
                            bus.post(new OnLoginSuccessful());
                            bus.post(new StreamRefreshedEvent());
                        },
                        this::handleError);
    }

    private void handleError(Throwable error) {
        if (error instanceof RetrofitError) {

            loginView.failureAnimation();
            new Handler().postDelayed(() ->
                    RetrofitErrorHelper.formatErrorMessageRx((RetrofitError) error)
                    .subscribe(s ->
                            loginView.toggleError(true, s)), 1200);
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
                            bus.post(new OnLoginSuccessful());
                        },
                        e -> {
                            loginView.failureAnimation();
                            handleError(e);
                        });
    }
}
