package com.lovecats.catlover.capsules.profile;

import com.lovecats.catlover.capsules.profile.interactor.ProfileInteractor;

public class ProfilePresenterImpl implements ProfilePresenter {

    private ProfileView profileView;
    private ProfileInteractor profileInteractor;

    public ProfilePresenterImpl(ProfileView profileView, ProfileInteractor profileInteractor) {
        this.profileView = profileView;
        this.profileInteractor = profileInteractor;
    }

    @Override
    public void onCreate() {
        if (profileInteractor.userLoggedIn()) {
            profileView.setUserName(profileInteractor.getUser().getUsername());
        }
        profileInteractor.getUser();
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

    @Override
    public void updateUserName(String userName) {
        profileInteractor.updateUserName(userName);
    }
}