package com.lovecats.catlover.capsules.drawer.view;

import android.app.Activity;

public interface NavigationPresenter {

    void onCreate();

    void onNavigationInteraction(Activity activity, int position);

    void onProfileClicked(Activity activity);
}
