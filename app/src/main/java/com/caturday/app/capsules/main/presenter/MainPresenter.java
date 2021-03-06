package com.caturday.app.capsules.main.presenter;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;

public interface MainPresenter {

    void create(Bundle savedInstanceState);

    void prepareOptionsMenu(Menu menu);

    void pauseSliderAnimation();

    void resumeSliderAnimation();

    String getCurrentUserId();

    void onActivityResult(int requestCode, int resultCode, Intent data);

    void onPause();

    void onResume();

    void performPlayServicesCheck();
}
