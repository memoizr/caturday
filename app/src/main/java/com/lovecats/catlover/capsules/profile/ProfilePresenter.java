package com.lovecats.catlover.capsules.profile;

/**
 * Created by user on 28/03/15.
 */
public interface ProfilePresenter {

    void logout();

    void onCreate();

    boolean userLoggedIn();

    void updateUserName(String userName);
}
