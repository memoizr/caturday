package com.lovecats.catlover.capsules.profile.view;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;

import com.astuetz.PagerSlidingTabStrip;
import com.google.common.eventbus.Subscribe;
import com.lovecats.catlover.R;
import com.lovecats.catlover.capsules.common.view.mvp.BaseActionBarActivity;
import com.lovecats.catlover.capsules.common.events.UserAvailableEvent;
import com.lovecats.catlover.capsules.dashboard.SlidingTabActivity;
import com.lovecats.catlover.capsules.profile.interactor.ProfileInteractor;
import com.lovecats.catlover.models.user.UserEntity;
import com.squareup.otto.Bus;
import com.squareup.otto.Produce;

import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class ProfilePresenterImpl implements ProfilePresenter<ProfileView> {

    private final Bus bus;
    private boolean registered = false;
    private ProfileView profileView;
    private ProfileInteractor profileInteractor;
    private UserEntity user;
    private Context context;

    public ProfilePresenterImpl(
            ProfileView profileView,
            ProfileInteractor profileInteractor,
            Bus bus) {
        this.profileView = profileView;
        this.profileInteractor = profileInteractor;
        this.bus = bus;
        System.out.println("prof pres created!!!!!!!");
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
        System.out.println("thisi is the id: " + id);
        System.out.println(savedInstanceState);

        if (savedInstanceState == null) {
            profileInteractor.getUserForId(id)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(this::setUser, Throwable::printStackTrace);
        } else {
            userAvailable(user);
        }

        profileView.setupCollapsibleToolbar(toolbar);
        initMenuClickListener(toolbar);
        initViewPager();
    }

    private void setUser(UserEntity userEntity) {
        this.user = userEntity;
        bus.post(new UserAvailableEvent(userEntity));
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
        return new UserAvailableEvent(user);
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