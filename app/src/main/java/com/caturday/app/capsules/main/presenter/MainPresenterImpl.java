package com.caturday.app.capsules.main.presenter;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.caturday.app.capsules.common.events.observablescrollview.OnScrollChangedEvent;
import com.caturday.app.capsules.common.events.observablescrollview.OnUpOrCancelMotionEvent;
import com.daimajia.slider.library.SliderLayout;
import com.daimajia.slider.library.SliderTypes.BaseSliderView;
import com.caturday.app.R;
import com.caturday.app.capsules.common.events.OnPageScrolledEvent;
import com.caturday.app.capsules.common.events.OnPreparePageScroll;
import com.caturday.app.capsules.common.events.StreamRefreshCompletedEvent;
import com.caturday.app.capsules.common.view.views.MovingImageSliderView;
import com.caturday.app.capsules.login.view.LoginActivity;
import com.caturday.app.capsules.main.interactor.MainInteractor;
import com.caturday.app.capsules.main.view.MainView;
import com.caturday.app.capsules.settings.SettingsActivity;
import com.caturday.app.models.catpost.CatPostEntity;
import com.caturday.app.util.animation.ImageAnimation;
import com.squareup.otto.Bus;
import com.squareup.otto.Subscribe;

import java.util.Collection;

import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class MainPresenterImpl implements MainPresenter {

    private final MainView mainView;
    private final Activity mainViewActivity;
    private final MainInteractor mainInteractor;
    private final Bus bus;
    private ImageAnimation backgroundImageAnimation;

    public MainPresenterImpl(MainView mainView, MainInteractor mainInteractor, Bus bus) {
        this.mainView = mainView;
        this.mainViewActivity = (Activity) mainView;
        this.mainInteractor = mainInteractor;
        this.bus = bus;
        bus.register(this);
    }

    @Subscribe
    public void onScrollChangedEvent(OnScrollChangedEvent e) {
        mainView.onScrollChanged(e.getScrollY(), e.isDragging());
    }

    @Subscribe
    public void onUpOrCancelMotionEvent(OnUpOrCancelMotionEvent e) {
        mainView.onUpOrCancelMotionEvent(e.getScrollState());
    }

    @Subscribe
    public void onPagerScrolled(OnPageScrolledEvent event) {
        int offset = event.getOffset();
        int position = event.getPosition();
        int collapsedThreshold = mainView.getCollapsedThreshold();

        if (offset >= 0) {
            if (offset > collapsedThreshold) {
                mainView.hideToolBarContainer(false);
                bus.post(new OnPreparePageScroll(collapsedThreshold, position));
            } else {
                bus.post(new OnPreparePageScroll(offset, position));
            }
        }
    }

    @Subscribe
    public void onRefreshComplete(StreamRefreshCompletedEvent event){
        mainView.onRefreshCompleted();
    }

    @Override
    public void create(Bundle savedInstanceState) {

        initSliderLayout(mainView.getSliderLayout());

        Toolbar toolbar = mainView.getToolbar();

        mainView.setUpFragments(savedInstanceState);

        mainView.setupCollapsibleToolbar(toolbar);

        initMenuClickListener(toolbar);
    }

    @Override
    public void prepareOptionsMenu(Menu menu) {
        MenuItem loginItem = menu.findItem(R.id.action_login);
        MenuItem logoutItem = menu.findItem(R.id.action_logout);
        if (loginItem != null && logoutItem != null) {
            loginItem.setVisible(!mainInteractor.userLoggedIn());
            logoutItem.setVisible(mainInteractor.userLoggedIn());
        }
    }

    @Override
    public void pauseSliderAnimation() {
        if (backgroundImageAnimation != null)
            backgroundImageAnimation.pauseAnimation();
    }

    @Override
    public void resumeSliderAnimation() {
        if (backgroundImageAnimation != null)
            backgroundImageAnimation.resumeAnimation();
    }

    private void initSliderLayout(SliderLayout sliderLayout) {

        getRandomPosts(10)
                .subscribeOn(Schedulers.io())
                .flatMap(Observable::from)
                .observeOn(AndroidSchedulers.mainThread())
                .retry()
                .subscribe((s) -> {
                    MovingImageSliderView defaultSliderView = new MovingImageSliderView(mainViewActivity);
                    defaultSliderView.image(s.getImageUrl())
                            .setScaleType(BaseSliderView.ScaleType.CenterCrop);

                    sliderLayout.addSlider(defaultSliderView);
                },
                        Throwable::printStackTrace
                );


        sliderLayout.setPresetTransformer(SliderLayout.Transformer.Fade);
        backgroundImageAnimation = new ImageAnimation();
        sliderLayout.setCustomAnimation(backgroundImageAnimation);
        sliderLayout.setDuration(10000);
        sliderLayout.startAutoCycle();
    }


    private void initMenuClickListener(Toolbar toolbar) {
        toolbar.setOnMenuItemClickListener(
                item -> {

                    mainView.toggleArrow(true);
                    pauseSliderAnimation();

                    if (item.getItemId() == R.id.action_login) {
                        Intent intent = new Intent(mainViewActivity, LoginActivity.class);
                        mainViewActivity.startActivity(intent);
                    } else if (item.getItemId() == R.id.action_logout) {
                        mainInteractor.performLogout();
                    } else {
                        Intent intent = new Intent(mainViewActivity, SettingsActivity.class);
                        mainViewActivity.startActivity(intent);
                    }
                    return true;
                });
    }

    private Observable<Collection<CatPostEntity>> getRandomPosts(int howMany) {
        return Observable.create(new Observable.OnSubscribe<Collection<CatPostEntity>>() {
            @Override
            public void call(Subscriber<? super Collection<CatPostEntity>> subscriber) {
                    subscriber.onNext(mainInteractor.getRandomCatPosts(howMany));
                    subscriber.onCompleted();
            }
        });
    }
}
