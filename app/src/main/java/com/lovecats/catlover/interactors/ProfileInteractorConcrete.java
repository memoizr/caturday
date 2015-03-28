package com.lovecats.catlover.interactors;

import android.content.Context;

import com.lovecats.catlover.data.UserModel;

/**
 * Created by user on 28/03/15.
 */
public class ProfileInteractorConcrete implements ProfileInteractor{

    private UserModel userModel;

    public ProfileInteractorConcrete(UserModel userModel){
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
