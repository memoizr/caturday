package com.lovecats.catlover.capsules.profile.view;

import android.app.Activity;
import android.content.Context;
import android.view.Menu;

public interface ProfilePresenter {

    void logout();

    void onCreate(Context context);

    boolean userLoggedIn();

    void updateUserName(String userName);

    void prepareOptionsMenu(Menu menu);
}
