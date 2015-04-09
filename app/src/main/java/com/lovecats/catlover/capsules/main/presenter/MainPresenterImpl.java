package com.lovecats.catlover.capsules.main.presenter;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.daimajia.slider.library.Indicators.PagerIndicator;
import com.daimajia.slider.library.SliderLayout;
import com.daimajia.slider.library.SliderTypes.BaseSliderView;
import com.lovecats.catlover.R;
import com.lovecats.catlover.capsules.common.view.views.MovingImageSliderView;
import com.lovecats.catlover.capsules.login.LoginActivity;
import com.lovecats.catlover.capsules.main.interactor.MainInteractor;
import com.lovecats.catlover.capsules.main.view.MainView;
import com.lovecats.catlover.capsules.settings.SettingsActivity;
import com.lovecats.catlover.models.catpost.CatPostEntity;
import com.lovecats.catlover.util.animation.ImageAnimation;

import java.util.Collection;

import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class MainPresenterImpl implements MainPresenter {

    private final MainView mainView;
    private final Activity mainViewActivity;
    private final MainInteractor mainInteractor;
    private ImageAnimation backgroundImageAnimation;

    public MainPresenterImpl(MainView mainView, MainInteractor mainInteractor) {
        this.mainView = mainView;
        this.mainViewActivity = (Activity) mainView;
        this.mainInteractor = mainInteractor;
    }

    @Override
    public void create(Bundle savedInstanceState) {

        initSliderLayout(mainView.getSliderLayout());

        Toolbar toolbar = mainView.getToolbar();

        mainView.setUpFragments(savedInstanceState);

        mainView.setupCollapsibleToolbar(toolbar);

        initMenuClickListener(toolbar);
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

    @Override
    public void prepareOptionsMenu(Menu menu) {
        MenuItem item = menu.findItem(R.id.action_login);
        if (item != null) {
//            item.setVisible(!userModel.userLoggedIn());
        }
    }

    @Override
    public void pauseSliderAnimation() {
        backgroundImageAnimation.pauseAnimation();
    }

    @Override
    public void resumeSliderAnimation() {
        backgroundImageAnimation.resumeAnimation();
    }

    private void initMenuClickListener(Toolbar toolbar) {
        toolbar.setOnMenuItemClickListener(
                item -> {

                    mainView.toggleArrow(true);
                    pauseSliderAnimation();

                    if (item.getItemId() == R.id.action_login) {
                        Intent intent = new Intent(mainViewActivity, LoginActivity.class);
                        mainViewActivity.startActivity(intent);
                    } else {
                        Intent intent = new Intent(mainViewActivity, SettingsActivity.class);
                        mainViewActivity.startActivity(intent);
                    }
                    return true;
                });
    }

    private void initSliderLayout(SliderLayout sliderLayout) {

        getRandomPosts(10)
                .subscribeOn(Schedulers.io())
                .flatMap((s) -> Observable.from(s))
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe((s) -> {
                    MovingImageSliderView defaultSliderView = new MovingImageSliderView(mainViewActivity);
                    defaultSliderView.image(s.getImageUrl())
                            .setScaleType(BaseSliderView.ScaleType.CenterCrop);

                    sliderLayout.addSlider(defaultSliderView);
                });

        sliderLayout.setPresetTransformer(SliderLayout.Transformer.Fade);
        backgroundImageAnimation = new ImageAnimation();
        sliderLayout.setCustomAnimation(backgroundImageAnimation);
        sliderLayout.setDuration(10000);
        sliderLayout.startAutoCycle();
    }
}
