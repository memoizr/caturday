package com.lovecats.catlover.ui.drawer;

import android.app.Activity;
import android.content.Intent;

import com.lovecats.catlover.interactors.navigation.NavigationInteractor;
import com.lovecats.catlover.ui.profile.ProfileActivity;


public class NavigationPresenterImpl implements NavigationPresenter {

    private NavigationView navigationView;
    private NavigationInteractor navigationInteractor;

    public NavigationPresenterImpl(NavigationView navigationView,
                                   NavigationInteractor navigationInteractor) {
        this.navigationView = navigationView;
        this.navigationInteractor = navigationInteractor;
    }

    @Override
    public void onCreate() {
        String[] values = navigationInteractor.provideNavigationItems();
        navigationView.initializeListView(values);
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
        activity.startActivity(profileIntent);
    }
}
