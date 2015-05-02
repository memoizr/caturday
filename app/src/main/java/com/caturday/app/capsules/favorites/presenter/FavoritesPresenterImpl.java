package com.caturday.app.capsules.favorites.presenter;

import android.content.Context;

import com.caturday.app.capsules.common.events.navigation.OnNavigationItemShownEvent;
import com.caturday.app.capsules.favorites.interactor.FavoritesInteractor;
import com.caturday.app.capsules.favorites.view.FavoritesView;
import com.squareup.otto.Bus;

import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class FavoritesPresenterImpl implements FavoritesPresenter {
    private final FavoritesView favoritesView;
    private final FavoritesInteractor favoritesInteractor;
    private final Bus bus;
    private Context context;

    public FavoritesPresenterImpl(FavoritesView favoritesView,
                                  FavoritesInteractor favoritesInteractor,
                                  Bus bus) {
        this.favoritesView = favoritesView;
        this.favoritesInteractor = favoritesInteractor;
        this.bus = bus;

    }

    @Override
    public void create(Context context) {
        this.context = context;
        favoritesView.initRecyclerView();
        favoritesInteractor.getFavoriteCatPosts()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(favoritesView::setRecyclerViewAdapter);

        bus.register(this);
        bus.post(new OnNavigationItemShownEvent(OnNavigationItemShownEvent.ITEM_FAVORITES));
    }
}
