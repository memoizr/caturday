package com.lovecats.catlover.capsules.drawer;

import android.app.Activity;

public interface NavigationPresenter {

    void onCreate();

    void onNavigationInteraction(Activity activity, int position);

    void onProfileClicked(Activity activity);
}
