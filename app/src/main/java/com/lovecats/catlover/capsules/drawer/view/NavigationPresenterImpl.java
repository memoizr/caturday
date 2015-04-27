package com.lovecats.catlover.capsules.drawer.view;

import android.app.Activity;
import android.content.Intent;

import com.lovecats.catlover.capsules.drawer.interactor.NavigationInteractor;
import com.lovecats.catlover.capsules.profile.view.ProfileActivity;
import com.lovecats.catlover.models.user.UserEntity;

public class NavigationPresenterImpl implements NavigationPresenter {

    private NavigationView navigationView;
    private NavigationInteractor navigationInteractor;
    private UserEntity userEntity;

    public NavigationPresenterImpl(NavigationView navigationView,
                                   NavigationInteractor navigationInteractor) {
        this.navigationView = navigationView;
        this.navigationInteractor = navigationInteractor;
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
        }
    }

    @Override
    public void onNavigationInteraction(Activity activity, int position) {
        if (activity instanceof DrawerActivity) {
            ((DrawerActivity)activity).onNavigationItemSelected(position);
        } else {
           throw new RuntimeException("Host activity must implement HostActivity interface");
        }
    }

    @Override
    public void onProfileClicked(Activity activity) {
        Intent profileIntent = new Intent(activity, ProfileActivity.class);
        System.out.println(userEntity);
        profileIntent.putExtra(ProfileActivity.EXTRA_ID, userEntity.getServerId());
        activity.startActivity(profileIntent);
    }
}
