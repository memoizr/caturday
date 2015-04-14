package com.lovecats.catlover.capsules.profile.view;

import android.content.Context;

import com.astuetz.PagerSlidingTabStrip;
import com.lovecats.catlover.capsules.common.BaseActionBarActivity;
import com.lovecats.catlover.capsules.profile.interactor.ProfileInteractor;

public class ProfilePresenterImpl implements ProfilePresenter {

    private ProfileView profileView;
    private ProfileInteractor profileInteractor;
    private Context context;

    public ProfilePresenterImpl(ProfileView profileView, ProfileInteractor profileInteractor) {
        this.profileView = profileView;
        this.profileInteractor = profileInteractor;
    }

    @Override
    public void onCreate(Context context) {
        this.context = context;
        if (profileInteractor.userLoggedIn()) {
            profileView.setUserName(profileInteractor.getUser().getUsername());
        }
        profileInteractor.getUser();
        profileView.showButton(userLoggedIn());
        profileView.initToolbar();
        initViewPager();
    }

    private void initViewPager() {

        ProfilePageAdapter adapter = new ProfilePageAdapter(((BaseActionBarActivity)context).getSupportFragmentManager());
//        PagerSlidingTabStrip pager = ((SlidingTabActivity) activity).getSlidingTabStrip();
        PagerSlidingTabStrip pager = null;
        profileView.initializePager(adapter, pager);
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