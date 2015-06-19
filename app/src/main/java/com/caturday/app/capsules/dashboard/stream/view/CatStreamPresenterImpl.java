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

import com.caturday.app.capsules.common.events.OnPageSelectedEvent;
import com.caturday.app.capsules.common.events.OnPostCreatedEvent;
import com.caturday.app.capsules.common.events.OnPostPagerScrolledEvent;
import com.caturday.app.capsules.common.events.navigation.OnNavigationItemShownEvent;
import com.caturday.app.capsules.detail.view.CatDetailActivity;
import com.caturday.app.capsules.detail.view.CatDetailPresenter;
import com.caturday.app.models.catpost.CatPostEntity;
import com.caturday.app.models.user.UserEntity;
import com.github.ksoichiro.android.observablescrollview.ScrollState;
import com.caturday.app.capsules.common.events.OnPagerScrolledEvent;
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
    private UserEntity currentUser;

    public CatStreamPresenterImpl(CatStreamView catStreamView,
                                  CatStreamInteractor catStreamInteractor,
                                  Bus eventBus) {
        this.catStreamView = catStreamView;
        this.catStreamInteractor = catStreamInteractor;
        this.eventBus = eventBus;
    }

    @Subscribe
    public void onNewPostCreated(OnPostCreatedEvent event) {
        catStreamInteractor.getCatPost(event.getServerId())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe( catPostEntity -> {
                    if (Objects.equals(catPostEntity.getCategory(), streamType)) {
                        catStreamView.getAdapter().addItem(catPostEntity);
                    }
                });
    }

    @Subscribe
    public void pageScrolled(OnPagerScrolledEvent event) {
        if (event.getPosition() == streamPosition) {
            int scrollOffset = catStreamView.getScrollPosition();
            eventBus.post(new OnPostPagerScrolledEvent(streamPosition, scrollOffset));
        }
    }

    @Subscribe
    public void onPageSelected(OnPageSelectedEvent event) {
        if (event.getPosition() == streamPosition) {
            catStreamView.onPageSelected();
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
    public void onDownMotionEvent() {}

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
        if (catStreamInteractor.userLoggedIn()) {
            this.currentUser = catStreamInteractor.getCurrentUser();
        }
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
                .subscribe((catPostCollection) -> {
                            if (catPostCollection.size() == 0) {
                                setEmptyView(true, false);
                            } else {
                                setEmptyView(false, false);
                                catStreamView.getAdapter().setItems(catPostCollection);
                            }
                        },
                        this::onLoadFailure);
    }

    private void setEmptyView(boolean showIt, boolean networkError) {
        catStreamView.showEmptyView(showIt, catStreamInteractor.userLoggedIn(), networkError);
    }

    private void onLoadFailure(Throwable e) {
        setEmptyView(true, true);
        e.printStackTrace();
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

    @Override
    public void onDestroyView() {
        eventBus.unregister(this);
    }

    @Override
    public boolean isCurrentUser(String serverId) {
        if (currentUser != null) {
            return Objects.equals(serverId, currentUser.getServerId());
        } else {
            return false;
        }
    }

    @Override
    public void deleteItem(String postId, int position) {
        catStreamInteractor.deletePost(postId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        s -> {
                            catStreamView.getAdapter().removeItem(position);
                        }
                        ,
                        e -> {e.printStackTrace();}
                );
    }
}
