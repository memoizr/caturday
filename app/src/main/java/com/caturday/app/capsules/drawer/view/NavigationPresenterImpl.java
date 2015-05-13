package com.caturday.app.capsules.drawer.view;

import android.app.Activity;
import android.content.Intent;

import com.caturday.app.R;
import com.caturday.app.capsules.common.events.navigation.OnNavigationItemShownEvent;
import com.caturday.app.capsules.drawer.interactor.NavigationInteractor;
import com.caturday.app.capsules.login.view.LoginActivity;
import com.caturday.app.capsules.profile.view.ProfileActivity;
import com.caturday.app.models.user.UserEntity;
import com.squareup.otto.Bus;
import com.squareup.otto.Produce;
import com.squareup.otto.Subscribe;

public class NavigationPresenterImpl implements NavigationPresenter {

    private final Bus bus;
    private NavigationView navigationView;
    private NavigationInteractor navigationInteractor;
    private UserEntity userEntity;
    private int currentItem;

    public NavigationPresenterImpl(NavigationView navigationView,
                                   NavigationInteractor navigationInteractor,
                                   Bus bus) {
        this.navigationView = navigationView;
        this.navigationInteractor = navigationInteractor;
        this.bus = bus;
    }

    @Override
    public void onCreate() {

        String[] values = navigationInteractor.provideNavigationItems();
        navigationView.initializeListView(values);

        boolean isUserLoggedIn = navigationInteractor.isUserLoggedIn();

        if (isUserLoggedIn) {
            userEntity = navigationInteractor.getLoggedInUser();
            navigationView.setUserEmail(userEntity.getEmail());
            navigationView.setUsername(userEntity.getUsername());
            navigationView.setUserProfileImage(userEntity.getImageUrl());
            navigationView.setUserCoverImage(userEntity.getCoverImageUrl());
        } else {

        }
        navigationView.userLoggedIn(isUserLoggedIn);
    }

    @Override
    public void onViewCreated() {
        bus.register(this);
    }

    @Produce
    public OnNavigationItemShownEvent onItemShownEvent() {

        return new OnNavigationItemShownEvent(currentItem);
    }

    @Override
    public void onNavigationInteraction(Activity activity, int position) {
        if (activity instanceof DrawerActivity) {
            ((DrawerActivity)activity).onNavigationItemSelected(position);
        } else {
           throw new RuntimeException("Host activity must implement HostActivity interface");
        }
    }

    @Subscribe
    public void onNavItemShown(OnNavigationItemShownEvent event) {

        currentItem = event.getCurrentItem();

        switch (event.getCurrentItem()){
            case OnNavigationItemShownEvent.ITEM_DASHBOARD: {

                navigationView.setSelected(0);
                break;
            }
            case OnNavigationItemShownEvent.ITEM_MY_OWN: {

                navigationView.setSelected(1);
                break;
            }
        }
    }

    @Override
    public void onProfileClicked(Activity activity, int x, int y) {
        boolean isUserLoggedIn = navigationInteractor.isUserLoggedIn();
        if (isUserLoggedIn) {
            Intent profileIntent = new Intent(activity, ProfileActivity.class);
            profileIntent.putExtra(ProfileActivity.EXTRA_ID, userEntity.getServerId());
            activity.startActivity(profileIntent);
        } else {
            Intent loginIntent = new Intent(activity, LoginActivity.class);
            loginIntent.putExtra(LoginActivity.RIPPLE_ORIGIN_X, x);
            loginIntent.putExtra(LoginActivity.RIPPLE_ORIGIN_Y, y);
            activity.startActivity(loginIntent);
        }
    }

    @Override
    public void onDestroy() {
        bus.unregister(this);
    }
}
