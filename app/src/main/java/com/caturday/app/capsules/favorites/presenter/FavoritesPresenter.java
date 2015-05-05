package com.caturday.app.capsules.favorites.presenter;

import android.content.Context;

import com.github.ksoichiro.android.observablescrollview.ObservableScrollViewCallbacks;

public interface FavoritesPresenter extends ObservableScrollViewCallbacks {

    void create(Context context);
}
