package com.caturday.app.capsules.main.presenter;

import android.os.Bundle;
import android.view.Menu;

public interface MainPresenter {

    void create(Bundle savedInstanceState);

    void prepareOptionsMenu(Menu menu);

    void pauseSliderAnimation();

    void resumeSliderAnimation();
}
