package com.caturday.app.capsules.dashboard.stream.view;

import android.app.Activity;
import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;

import com.github.ksoichiro.android.observablescrollview.ScrollState;
import com.caturday.app.capsules.common.events.OnPageScrolledEvent;
import com.caturday.app.capsules.common.events.OnPreparePageScroll;
import com.caturday.app.capsules.common.events.StreamRefreshCompletedEvent;
import com.caturday.app.capsules.common.events.StreamRefreshedEvent;
import com.caturday.app.capsules.common.listener.ScrollEventListener;
import com.caturday.app.capsules.dashboard.stream.interactor.CatStreamInteractor;
import com.squareup.otto.Bus;
import com.squareup.otto.Subscribe;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class CatStreamPresenterImpl extends CatStreamPresenter {

    private final CatStreamView catStreamView;
    private final CatStreamInteractor catStreamInteractor;
    private final Bus eventBus;
    private ScrollEventListener scrollEventListener;
    private static final String WARN_NO_LISTENER = "No Listeners attached";
    private Context context;
    private String streamType;
    private int streamPosition;

    public CatStreamPresenterImpl(CatStreamView catStreamView,
                                  CatStreamInteractor catStreamInteractor,
                                  Bus eventBus) {
        this.catStreamView = catStreamView;
        this.catStreamInteractor = catStreamInteractor;
        this.eventBus = eventBus;

        eventBus.register(this);
    }

    @Subscribe
    public void pageScrolled(OnPageScrolledEvent event) {
        if (event.getPosition() == streamPosition && event.getOffset() < 0) {
            int scrollOffset = catStreamView.getScrollPosition();
            eventBus.post(new OnPageScrolledEvent(streamPosition, scrollOffset));
        }
    }

    @Subscribe
    public void prepareScroll(OnPreparePageScroll event) {
        int sourcePagePosition = event.getSourcePagePosition();
        if (streamPosition == sourcePagePosition + 1 || streamPosition == sourcePagePosition -1) {
            catStreamView.setScrollPosition(event.getTargetScroll());
        }
    }

    @Subscribe
    public void refreshHappened(StreamRefreshedEvent event) {
        refreshCollection(streamType);
    }

    @Override
    public void onScrollChanged(int i, boolean firstScroll, boolean dragging) {
        scrollEventListener.onScrollChanged(i, dragging);
    }

    @Override
    public void onDownMotionEvent() {

    }

    @Override
    public void onUpOrCancelMotionEvent(ScrollState scrollState) {
        scrollEventListener.onUpOrCancelMotionEvent(scrollState);
    }

    @Override
    public void onAttach(Activity activity) {

        this.context = activity;

        if (activity instanceof ScrollEventListener) {
            scrollEventListener = (ScrollEventListener) activity;
        } else {
            Log.e(WARN_NO_LISTENER, "The parent activity is not implementing ScrollEventListener");
        }
    }

    @Override
    public void onViewCreated(String streamType, int streamPosition) {
        this.streamType = streamType;
        this.streamPosition = streamPosition;
        LinearLayoutManager layoutManager = new LinearLayoutManager(context);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        catStreamView.initializeRecyclerView(this, layoutManager);
    }

    @Override
    public void onScrollStateChanged(int scrollState) {
        scrollEventListener.onScrollStateChanged(scrollState);
    }

    private void refreshCollection(String streamType) {
        this.streamType = streamType;
        Observable.just(1)
                .observeOn(Schedulers.io())
                .doOnEach((s) -> {
                    catStreamInteractor.eraseCache();
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(s -> onRefreshComplete(), Throwable::printStackTrace);
    }

    private void onRefreshComplete() {
        setAdapterByType(streamType);
        eventBus.post(new StreamRefreshCompletedEvent());
    }

    @Override
    public void setAdapterByType(String streamType) {
        this.streamType = streamType;
        catStreamInteractor.getCatPostPageAndType(0,
                streamType,
                false).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe((catPostCollection) -> catStreamView.getAdapter().setItems(catPostCollection),
                        Throwable::printStackTrace);
    }

    @Override
    public void loadMore(int page, int totalItems) {
        catStreamInteractor.getCatPostPageAndType(page,
                streamType,
                false).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe((catPostCollection) -> catStreamView.getAdapter().addItems(catPostCollection),
                        Throwable::printStackTrace);
    }

    @Override
    public void plusOneClicked(String serverId, int position) {
       catStreamInteractor.catPostVoted(serverId)
               .subscribeOn(Schedulers.io())
               .observeOn(AndroidSchedulers.mainThread())
               .subscribe(s -> catStreamView.getAdapter().updateItem(position, s),
                       Throwable::printStackTrace);
    }
}
