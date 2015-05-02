package com.caturday.app.capsules.drawer.view;

import android.app.Activity;

public interface NavigationPresenter {

    void onCreate();

    void onViewCreated();

    void onNavigationInteraction(Activity activity, int position);

    void onProfileClicked(Activity activity);

    void onDestroy();

    interface NavigationView {

        void initializeListView(String[] values);

        void setUserEmail(String userEmail);

        void setUsername(String username);

        void setSelected(int position);
    }
}
