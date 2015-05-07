package com.caturday.app.capsules.profile.view;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;

import com.astuetz.PagerSlidingTabStrip;
import com.caturday.app.R;
import com.caturday.app.capsules.common.events.navigation.OnActivityResultEvent;
import com.caturday.app.capsules.common.view.mvp.BaseActionBarActivity;
import com.caturday.app.capsules.common.events.UserAvailableEvent;
import com.caturday.app.capsules.dashboard.SlidingTabActivity;
import com.caturday.app.capsules.profile.interactor.ProfileInteractor;
import com.caturday.app.models.user.UserEntity;
import com.squareup.otto.Bus;
import com.squareup.otto.Produce;

import java.util.Objects;

import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class ProfilePresenterImpl implements ProfilePresenter<ProfileView> {

    private final Bus bus;
    private boolean registered = false;
    private ProfileView profileView;
    private ProfileInteractor profileInteractor;
    private UserEntity user;
    private Context context;
    private boolean isCurrentUser;

    public ProfilePresenterImpl(
            ProfileView profileView,
            ProfileInteractor profileInteractor,
            Bus bus) {
        this.profileView = profileView;
        this.profileInteractor = profileInteractor;
        this.bus = bus;
    }

    private void registerBus(){
        if (!registered) {
            bus.register(this);
            registered = true;
        }
    }

    @Override
    public void onCreate(Context context, Bundle savedInstanceState) {
        this.context = context;
        Toolbar toolbar = profileView.getToolbar();

        String id = ((Activity) context).getIntent().getExtras().getString(ProfileActivity.EXTRA_ID);

        if (savedInstanceState == null) {
            UserEntity currentUser = profileInteractor.getCurrentUser();
            isCurrentUser = Objects.equals(currentUser.getServerId(), id);
            if (profileInteractor.userLoggedIn() && isCurrentUser) {
                user = currentUser;
                setUser(profileInteractor.getCurrentUser(), true);
            } else {
                getUser(id);
            }
        } else {
            userAvailable(user);
        }

        profileView.setupCollapsibleToolbar(toolbar);
        initMenuClickListener(toolbar);
        initViewPager();
    }

    private void getUser(String id) {
        profileInteractor.getUserForId(id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(user -> setUser(user, false), Throwable::printStackTrace);
    }

    private void setUser(UserEntity userEntity, boolean isCurrentUser) {
        this.user = userEntity;
        bus.post(new UserAvailableEvent(userEntity, isCurrentUser));
        userAvailable(user);
        registerBus();
    }

    public void userAvailable(UserEntity user) {
        profileView.setUsername(user.getUsername());
        profileView.setProfileImage(user.getImageUrl());
        profileView.setCoverImage(user.getCoverImageUrl());
    }

    @Produce
    public UserAvailableEvent produceUser() {
        return new UserAvailableEvent(user, isCurrentUser);
    }

    private void initViewPager() {

        ProfilePageAdapter adapter =
                new ProfilePageAdapter(((BaseActionBarActivity) context).getSupportFragmentManager());
        PagerSlidingTabStrip pager = ((SlidingTabActivity) context).getSlidingTabStrip();
        profileView.initializePager(adapter, pager);
    }

    @Override
    public void logout() {
        profileInteractor.logout();
    }

    @Override
    public boolean userLoggedIn() {
        return profileInteractor.userLoggedIn();
    }

    @Override
    public void updateUserName(String userName) {
        profileInteractor.updateUserName(userName);
    }

    @Override
    public void prepareOptionsMenu(Menu menu) {

    }

    @Override
    public void onDestroy() {
        bus.unregister(this);
    }

    @Override
    public void bindView(ProfileView view) {
        this.profileView = view;
    }

    @Override
    public void onResume() {
//        bus.post(new UserAvailableEvent(user));
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        bus.post(new OnActivityResultEvent(requestCode, resultCode, data));
    }

    private void initMenuClickListener(Toolbar toolbar) {
        toolbar.setOnMenuItemClickListener(
                item -> {
                    if (item.getItemId() == R.id.action_follow) {

                        profileInteractor.followUser(user.getServerId())
                                .subscribeOn(Schedulers.io())
                                .observeOn(AndroidSchedulers.mainThread())
                                .subscribe(System.out::println, Throwable::printStackTrace);

                    } else if (item.getItemId() == R.id.action_unfollow) {

                        profileInteractor.unfollowUser(user.getServerId())
                                .subscribeOn(Schedulers.io())
                                .observeOn(AndroidSchedulers.mainThread())
                                .subscribe(System.out::println, Throwable::printStackTrace);

                    }
                    return true;
                });
    }
}