package com.caturday.app.capsules.login.view;

public interface LoginView {

    void successAnimation();

    void toggleError(boolean showError, String errorMessage);

    void failureAnimation();
}
