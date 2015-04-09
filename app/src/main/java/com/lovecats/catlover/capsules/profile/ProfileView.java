package com.lovecats.catlover.capsules.profile;

/**
 * Created by user on 28/03/15.
 */
public interface ProfileView {

    void onPostLogout();

    void showButton(Boolean visible);

    void setUserName(String string);
}
