package com.caturday.app.capsules.login.view;


import com.caturday.app.capsules.login.interactor.LoginInteractor;

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
        String message = error.getMessage();
        if (message.contains("401")) {
            loginView.toggleError(true, "Invalid email or password");
        } else {
            loginView.toggleError(false, "");
        }
        loginView.failureAnimation();
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
                            e.printStackTrace();
                        });
    }
}
