package com.lovecats.catlover.ui.profile;

import com.lovecats.catlover.interactors.ProfileInteractor;

/**
 * Created by user on 28/03/15.
 */
public class ProfilePresenterConcrete implements ProfilePresenter {

    private ProfileView profileView;
    private ProfileInteractor profileInteractor;

    public ProfilePresenterConcrete(ProfileView profileView, ProfileInteractor profileInteractor) {
        this.profileView = profileView;
        this.profileInteractor = profileInteractor;
    }

    @Override
    public void logout() {
        profileInteractor.logout();
        profileView.onPostLogout();
        profileView.showButton(false);
    }

    @Override
    public boolean userLoggedIn() {
        return profileInteractor.userLoggedIn();
    }
}
