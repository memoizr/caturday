package com.lovecats.catlover.capsules.profile.view;

import android.app.Activity;
import android.content.Context;

import com.astuetz.PagerSlidingTabStrip;
import com.lovecats.catlover.capsules.common.BaseActionBarActivity;
import com.lovecats.catlover.capsules.common.events.UserAvailableEvent;
import com.lovecats.catlover.capsules.dashboard.SlidingTabActivity;
import com.lovecats.catlover.capsules.profile.interactor.ProfileInteractor;
import com.lovecats.catlover.models.user.UserEntity;
import com.squareup.otto.Bus;

import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class ProfilePresenterImpl implements ProfilePresenter {

    private final Bus bus;
    private ProfileView profileView;
    private ProfileInteractor profileInteractor;
    private Context context;
    private UserEntity user;

    public ProfilePresenterImpl(ProfileView profileView,
                                ProfileInteractor profileInteractor,
                                Bus bus) {
        this.profileView = profileView;
        this.profileInteractor = profileInteractor;
        this.bus = bus;
    }

    @Override
    public void onCreate(Context context) {
        this.context = context;
        String id = ((Activity) context).getIntent().getExtras().getString(ProfileActivity.EXTRA_ID);
        profileInteractor.getUserForId(id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::setUser, Throwable::printStackTrace);

        profileView.showButton(userLoggedIn());
        profileView.setupCollapsibleToolbar(profileView.getToolbar());
        initViewPager();
    }

    private void setUser(UserEntity userEntity) {
        profileView.setUsername(userEntity.getUsername());
        profileView.setProfileImage(userEntity.getImageUrl());
        profileView.setCoverImage(userEntity.getCoverImageUrl());
        bus.post(new UserAvailableEvent(userEntity));

        System.out.println(bus.hashCode());
    }

    private void initViewPager() {

        ProfilePageAdapter adapter =
                new ProfilePageAdapter(((BaseActionBarActivity)context).getSupportFragmentManager());
        PagerSlidingTabStrip pager = ((SlidingTabActivity) context).getSlidingTabStrip();
        profileView.initializePager(adapter, pager);
    }

    @Override
    public void logout() {
        profileInteractor.logout();
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