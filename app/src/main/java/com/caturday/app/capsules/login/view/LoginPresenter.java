package com.caturday.app.capsules.login.view;

public interface LoginPresenter {

    void performLogin(String email, String password);

    void performSignup(String username, String email, String password);
}
