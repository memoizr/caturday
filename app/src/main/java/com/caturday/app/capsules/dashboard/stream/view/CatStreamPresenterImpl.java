package com.caturday.app.capsules.dashboard.stream.view;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.util.Pair;
import android.support.v4.view.ViewCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.View;

import com.caturday.app.capsules.common.events.OnPostCreatedEvent;
import com.caturday.app.capsules.common.events.navigation.OnNavigationItemShownEvent;
import com.caturday.app.capsules.detail.view.CatDetailActivity;
import com.caturday.app.capsules.detail.view.CatDetailPresenter;
import com.caturday.app.capsules.detail.view.CatDetailPresenterImpl;
import com.caturday.app.models.catpost.CatPostEntity;
import com.github.ksoichiro.android.observablescrollview.ScrollState;
import com.caturday.app.capsules.common.events.OnPageScrolledEvent;
import com.caturday.app.capsules.common.events.OnPreparePageScroll;
import com.caturday.app.capsules.common.events.StreamRefreshCompletedEvent;
import com.caturday.app.capsules.common.events.StreamRefreshedEvent;
import com.caturday.app.capsules.common.listener.ScrollEventListener;
import com.caturday.app.capsules.dashboard.stream.interactor.CatStreamInteractor;
import com.squareup.otto.Bus;
import com.squareup.otto.Subscribe;

import java.util.Objects;

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
    private String userId;

    public CatStreamPresenterImpl(CatStreamView catStreamView,
                                  CatStreamInteractor catStreamInteractor,
                                  Bus eventBus) {
        this.catStreamView = catStreamView;
        this.catStreamInteractor = catStreamInteractor;
        this.eventBus = eventBus;

    }

    @Subscribe
    public void onNewPostCreated(OnPostCreatedEvent event) {
        System.out.println("New post created yo");
        catStreamInteractor.getCatPost(event.getServerId())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe( catPostEntity -> {
                    System.out.println(catPostEntity.getCategory() + " " + streamType);
                    if (Objects.equals(catPostEntity.getCategory(), streamType)) {
                        System.out.println("they're equal, yo");
                        catStreamView.getAdapter().addItem(catPostEntity);
                    }
                });
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

    public void onViewCreated(String streamType, String userId, int streamPosition) {
        this.streamType = streamType;
        this.streamPosition = streamPosition;
        this.userId = userId;
        LinearLayoutManager layoutManager = new LinearLayoutManager(context);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        catStreamView.initializeRecyclerView(this, layoutManager);

        if (userId != null)
            eventBus.post(new OnNavigationItemShownEvent(OnNavigationItemShownEvent.ITEM_MY_OWN));

        eventBus.register(this);
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
        setAdapter();
        eventBus.post(new StreamRefreshCompletedEvent());
    }

    @Override
    public void setAdapter() {

        catStreamInteractor.getCatPostPageAndType(0,
                userId != null ? userId : streamType,
                userId != null)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe((catPostCollection) -> catStreamView.getAdapter().setItems(catPostCollection),
                        Throwable::printStackTrace);
    }

    @Override
    public void loadMore(int page, int totalItems) {

        catStreamInteractor.getCatPostPageAndType(page,
                userId != null ? userId : streamType,
                userId != null)
                .subscribeOn(Schedulers.io())
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

    @Override
    public void openDetails(int i, View view, CatPostEntity catPostEntity, boolean showComments) {

        final String transitionName = CatDetailPresenter.EXTRA_TRANSITION_NAME + i;
        ViewCompat.setTransitionName(view, transitionName);
        Intent intent = new Intent(context, CatDetailActivity.class);
        ActivityOptionsCompat options =
                ActivityOptionsCompat.makeSceneTransitionAnimation(
                        (Activity) context,
                        Pair.create(view, transitionName)
                );

        intent.putExtra(CatDetailPresenter.EXTRA_TRANSITION, transitionName);
        intent.putExtra(CatDetailPresenter.EXTRA_URL, catPostEntity.getImageUrl());
        intent.putExtra(CatDetailPresenter.EXTRA_SERVER_ID, catPostEntity.getServerId());
        intent.putExtra(CatDetailPresenter.EXTRA_SHOW_COMMENTS, showComments);
        ActivityCompat.startActivity((Activity) context, intent, options.toBundle());
    }
}
