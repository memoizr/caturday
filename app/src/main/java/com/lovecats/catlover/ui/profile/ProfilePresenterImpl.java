package com.lovecats.catlover.ui.profile;

import com.lovecats.catlover.interactors.profile.ProfileInteractor;

/**
 * Created by user on 28/03/15.
 */
public class ProfilePresenterImpl implements ProfilePresenter {

    private ProfileView profileView;
    private ProfileInteractor profileInteractor;

    public ProfilePresenterImpl(ProfileView profileView, ProfileInteractor profileInteractor) {
        this.profileView = profileView;
        this.profileInteractor = profileInteractor;
    }

    @Override
    public void onCreate() {
        profileView.showButton(userLoggedIn());
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
