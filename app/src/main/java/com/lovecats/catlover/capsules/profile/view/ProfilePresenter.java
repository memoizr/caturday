package com.lovecats.catlover.capsules.profile.view;

import android.app.Activity;
import android.content.Context;

/**
 * Created by user on 28/03/15.
 */
public interface ProfilePresenter {

    void logout();

    void onCreate(Context context);

    boolean userLoggedIn();

    void updateUserName(String userName);
}
