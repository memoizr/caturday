package com.caturday.app.capsules.profile.view;

import android.content.Context;
import android.os.Bundle;
import android.view.Menu;

public interface ProfilePresenter<T> {

    void logout();

    void onCreate(Context context, Bundle bundle);

    boolean userLoggedIn();

    void updateUserName(String userName);

    void prepareOptionsMenu(Menu menu);

    void onDestroy();

    void bindView(T view);

    void onResume();
}
