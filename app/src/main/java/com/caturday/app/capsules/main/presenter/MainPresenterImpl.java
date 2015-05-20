package com.caturday.app.capsules.main.presenter;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.caturday.app.capsules.common.events.OnLogoutSuccessful;
import com.caturday.app.capsules.common.events.OnPostCreatedEvent;
import com.caturday.app.capsules.common.events.OnPostPagerScrolledEvent;
import com.caturday.app.capsules.common.events.OnPostResult;
import com.caturday.app.capsules.common.events.StreamRefreshedEvent;
import com.caturday.app.capsules.common.events.navigation.OnNavigationItemShownEvent;
import com.caturday.app.capsules.common.events.observablescrollview.OnScrollChangedEvent;
import com.caturday.app.capsules.common.events.observablescrollview.OnUpOrCancelMotionEvent;
import com.caturday.app.capsules.newpost.view.NewPostActivity;
import com.caturday.app.util.Tuple;
import com.daimajia.slider.library.Indicators.PagerIndicator;
import com.daimajia.slider.library.SliderLayout;
import com.daimajia.slider.library.SliderTypes.BaseSliderView;
import com.caturday.app.R;
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
import java.util.concurrent.TimeUnit;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class MainPresenterImpl implements MainPresenter {

    private static final int LOGIN_REQUEST = 534;
    private final MainView mainView;
    private final Activity mainViewActivity;
    private final MainInteractor mainInteractor;
    private final Bus bus;
    private ImageAnimation backgroundImageAnimation;
    private SliderLayout sliderLayout;

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
    public void onPagerScrolled(OnPostPagerScrolledEvent event) {
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
        sliderLayout.stopAutoCycle();

    }

    @Override
    public void resumeSliderAnimation() {
        if (backgroundImageAnimation != null)
            backgroundImageAnimation.resumeAnimation();
        sliderLayout.startAutoCycle();
    }

    @Override
    public String getCurrentUserId() {
        String userId = "";
        if (mainInteractor.userLoggedIn()) {
            userId = mainInteractor.getCurrentUser().getServerId();
        }
        return userId;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        bus.post(new OnPostResult());
        if (requestCode == NewPostActivity.NEW_POST_REQUEST_CODE &&
                resultCode == Activity.RESULT_OK) {
            bus.post(new OnPostCreatedEvent(data.getExtras().getString(NewPostActivity.NEW_POST_ID)));
        } else if (requestCode == LOGIN_REQUEST) {
            mainView.toggleArrow(false);
        }
    }

    @Override
    public void onPause() {
        pauseSliderAnimation();
    }

    @Override
    public void onResume() {
        resumeSliderAnimation();
    }

    private void initSliderLayout(SliderLayout sliderLayout) {

        this.sliderLayout = sliderLayout;
        getRandomPosts(10)
                .subscribeOn(Schedulers.io())
                .flatMap(Observable::from)
                .observeOn(AndroidSchedulers.mainThread())
                .retryWhen(notification ->
                                notification
                                        .zipWith(Observable.range(0, 30), (x, y) ->
                                                        new Tuple<>(x, y)
                                        )
                                        .flatMap(s -> s.y == 50 ?
                                                        Observable.error(s.x) :
                                                        Observable.timer(500, TimeUnit.MILLISECONDS)
                                        )
                )
                .subscribe((s) -> {
                            MovingImageSliderView defaultSliderView = new MovingImageSliderView(mainViewActivity);
                            defaultSliderView
                                    .image(s.getImageUrl())
                                    .empty(R.drawable.solid_primary)
                                    .setScaleType(BaseSliderView.ScaleType.CenterCrop);

                            sliderLayout.addSlider(defaultSliderView);
                        },
                        Throwable::printStackTrace
                );

        sliderLayout.setIndicatorVisibility(PagerIndicator.IndicatorVisibility.Invisible);
        sliderLayout.setPresetTransformer(SliderLayout.Transformer.Fade);
        backgroundImageAnimation = new ImageAnimation();
        sliderLayout.setCustomAnimation(backgroundImageAnimation);
        sliderLayout.setDuration(10000);
        sliderLayout.startAutoCycle();
    }

    @Subscribe
    public void onNavItemShown(OnNavigationItemShownEvent event) {

        switch (event.getCurrentItem()){
            case OnNavigationItemShownEvent.ITEM_DASHBOARD:
                mainView.showTabs(true);
                break;
            default:
                mainView.showTabs(false);
                break;
        }
    }


    private void initMenuClickListener(Toolbar toolbar) {
        toolbar.setOnMenuItemClickListener(
                item -> {
                    if (item.getItemId() == R.id.action_login) {
                        Intent intent = new Intent(mainViewActivity, LoginActivity.class);
                        int x = toolbar.getWidth() -
                                mainViewActivity.getResources().getDimensionPixelSize(R.dimen.overflow_x);
                        int y = mainViewActivity.getResources().getDimensionPixelSize(R.dimen.overflow_y);
                        intent.putExtra(LoginActivity.RIPPLE_ORIGIN_X, x);
                        intent.putExtra(LoginActivity.RIPPLE_ORIGIN_Y, y);
                        mainViewActivity.startActivityForResult(intent, LOGIN_REQUEST);
                    } else if (item.getItemId() == R.id.action_logout) {
                        mainInteractor.performLogout();
                        bus.post(new OnLogoutSuccessful());
                    }else if (item.getItemId() == R.id.action_refresh) {
                        bus.post(new StreamRefreshedEvent());
                    } else {
                        Intent intent = new Intent(mainViewActivity, SettingsActivity.class);
                        mainViewActivity.startActivity(intent);
                    }
                    return true;
                });
    }

    private Observable<Collection<CatPostEntity>> getRandomPosts(int howMany) {
        return Observable.defer(() -> Observable.create(subscriber -> {

            try {
                Collection<CatPostEntity> catPosts = mainInteractor.getRandomCatPosts(howMany);
                subscriber.onNext(catPosts);
                subscriber.onCompleted();
                mainView.setSliderBackgroundTransparent(true);
            } catch (Exception e) {
                subscriber.onError(new Exception("No posts found"));
            }
        }));
    }
}
