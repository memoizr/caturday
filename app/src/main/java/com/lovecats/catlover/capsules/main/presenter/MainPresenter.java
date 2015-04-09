package com.lovecats.catlover.capsules.main.presenter;

import android.os.Bundle;
import android.view.Menu;

/**
 * Created by user on 28/03/15.
 */
public interface MainPresenter {
    void create(Bundle savedInstanceState);

    void prepareOptionsMenu(Menu menu);

    void pauseSliderAnimation();

    void resumeSliderAnimation();
}
