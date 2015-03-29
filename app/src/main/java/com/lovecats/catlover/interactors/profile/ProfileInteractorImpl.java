package com.lovecats.catlover.interactors.profile;

import com.lovecats.catlover.data.UserModel;

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
