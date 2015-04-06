package com.lovecats.catlover.capsules.drawer;

import android.app.Activity;

/**
 * Created by user on 28/03/15.
 */
public interface NavigationPresenter {

    public void onCreate();

    public void onNavigationInteraction(Activity activity, int position);

    public void onProfileClicked(Activity activity);
}
