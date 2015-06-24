package com.caturday.app.capsules.favorites.presenter;

import android.content.Context;

import com.caturday.app.capsules.common.events.navigation.OnNavigationItemShownEvent;
import com.caturday.app.capsules.common.events.observablescrollview.OnScrollChangedEvent;
import com.caturday.app.capsules.common.events.observablescrollview.OnUpOrCancelMotionEvent;
import com.caturday.app.capsules.favorites.interactor.FavoritesInteractor;
import com.caturday.app.capsules.favorites.view.FavoritesView;
import com.github.ksoichiro.android.observablescrollview.ScrollState;
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
                .subscribe(favoritesView::setRecyclerViewAdapter,
                                    Throwable::printStackTrace
                        );

        bus.register(this);
        bus.post(new OnNavigationItemShownEvent(OnNavigationItemShownEvent.ITEM_MY_OWN));
    }

    // TODO use rx instead of churning memory like a madman

    @Override
    public void onScrollChanged(int scrollY, boolean firstScroll, boolean dragging) {
        bus.post(new OnScrollChangedEvent(scrollY, firstScroll, dragging));
    }

    @Override public void onDownMotionEvent() { }

    @Override
    public void onUpOrCancelMotionEvent(ScrollState scrollState) {
        bus.post(new OnUpOrCancelMotionEvent(scrollState));
    }
}
