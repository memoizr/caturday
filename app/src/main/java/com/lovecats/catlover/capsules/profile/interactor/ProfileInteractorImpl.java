package com.lovecats.catlover.capsules.profile.interactor;

import com.lovecats.catlover.data.user.UserModel;

/**
 * Created by user on 28/03/15.
 */
public class ProfileInteractorImpl implements ProfileInteractor{

    private UserModel userModel;

    public ProfileInteractorImpl(UserModel userModel){
        this.userModel = userModel;
    }

    @Override
    public void logout() {
        userModel.flushUsers();
    }

    @Override
    public boolean userLoggedIn() {
        return userModel.userLoggedIn();
    }
}
