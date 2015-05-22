package com.caturday.app.capsules.main.presenter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.caturday.app.capsules.common.events.OnLogoutSuccessful;
import com.caturday.app.capsules.common.events.OnPostCreatedEvent;
import com.caturday.app.capsules.common.events.OnPostPagerScrolledEvent;
import com.caturday.app.capsules.common.events.OnPostResult;
import com.caturday.app.capsules.common.events.StreamRefreshedEvent;
import com.caturday.app.capsules.common.events.navigation.OnNavigationItemShownEvent;
import com.caturday.app.capsules.common.events.observablescrollview.OnScrollChangedEvent;
import com.caturday.app.capsules.common.events.observablescrollview.OnUpOrCancelMotionEvent;
import com.caturday.app.capsules.main.view.MainActivity;
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
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.gcm.GoogleCloudMessaging;
import com.squareup.otto.Bus;
import com.squareup.otto.Subscribe;

import java.io.IOException;
import java.util.Collection;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class MainPresenterImpl implements MainPresenter {

    public static final String EXTRA_MESSAGE = "message";
    public static final String PROPERTY_REG_ID = "registration_id";
    private static final String PROPERTY_APP_VERSION = "appVersion";
    private final static int PLAY_SERVICES_RESOLUTION_REQUEST = 9000;

    String SENDER_ID = "79202610531 ";

    static final String TAG = "MainActivity";

    GoogleCloudMessaging gcm;
    AtomicInteger msgId = new AtomicInteger();
    SharedPreferences prefs;

    String regid;

    private static final int LOGIN_REQUEST = 534;
    private final MainView mainView;
    private final Activity mainViewContext;
    private final MainInteractor mainInteractor;
    private final Bus bus;
    private ImageAnimation backgroundImageAnimation;
    private SliderLayout sliderLayout;

    public MainPresenterImpl(MainView mainView, MainInteractor mainInteractor, Bus bus) {
        this.mainView = mainView;
        this.mainViewContext = (Activity) mainView;
        this.mainInteractor = mainInteractor;
        this.bus = bus;
        bus.register(this);
    }

    @Override
    public void create(Bundle savedInstanceState) {

        initSliderLayout(mainView.getSliderLayout());

        Toolbar toolbar = mainView.getToolbar();

        mainView.setUpFragments(savedInstanceState);

        mainView.setupCollapsibleToolbar(toolbar);

        initMenuClickListener(toolbar);

        performPlayServicesCheck();
    }

    @Override
    public void performPlayServicesCheck() {
        if (checkPlayServices()) {
            gcm = GoogleCloudMessaging.getInstance(mainViewContext.getApplicationContext());
            regid = getRegistrationId(mainViewContext);

            if (regid.isEmpty()) {
                registerWithGcm()
                        .flatMap(s -> mainInteractor.registerDevice(regid))
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(s -> {
                            Log.i(TAG, s.getRegistrationId());
                        }, e -> e.printStackTrace());
            }
        } else {
            Toast.makeText(mainViewContext, "You need Google Play Services to use this app", Toast.LENGTH_LONG);
        }
    }

    private String getRegistrationId(Context context) {
        final SharedPreferences prefs = getGCMPreferences(context);
        String registrationId = prefs.getString(PROPERTY_REG_ID, "");
        Log.i(TAG, "Looking for registration...");
        if (registrationId.isEmpty()) {
            Log.i(TAG, "Registration not found.");
            return "";
        }

        // Check if app was updated; if so, it must clear the registration ID
        // since the existing registration ID is not guaranteed to work with
        // the new app version.
        int registeredVersion = prefs.getInt(PROPERTY_APP_VERSION, Integer.MIN_VALUE);
        int currentVersion = getAppVersion(context);
        if (registeredVersion != currentVersion) {
            Log.i(TAG, "App version changed.");
            return "";
        }
        return registrationId;
    }

    private Observable<String> registerWithGcm() {
        return Observable.defer(() -> Observable.create((s) -> {
                    String msg = "";
                    try {
                        if (gcm == null) {
                            gcm = GoogleCloudMessaging.getInstance(mainViewContext.getApplicationContext());
                        }
                        regid = gcm.register(SENDER_ID);
                        msg = "Device registered, registration ID=" + regid;

                        // You should send the registration ID to your server over HTTP,
                        // so it can use GCM/HTTP or CCS to send messages to your app.
                        // The request to your server should be authenticated if your app
                        // is using accounts.

                        // For this demo: we don't need to send it because the device
                        // will send upstream messages to a server that echo back the
                        // message using the 'from' address in the message.

                        // Persist the registration ID - no need to register again.
                        storeRegistrationId(mainViewContext.getApplicationContext(), regid);
                        s.onNext(regid);
                    } catch (IOException ex) {
                        msg = "Error :" + ex.getMessage();
                        // If there is an error, don't just keep trying to register.
                        // Require the user to click a button again, or perform
                        // exponential back-off.
                        s.onError(ex);
                    }
                }
        ));
    }

    private void storeRegistrationId(Context context, String regId) {
        final SharedPreferences prefs = getGCMPreferences(context);
        int appVersion = getAppVersion(context);
        Log.i(TAG, "Saving regId on app version " + appVersion);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString(PROPERTY_REG_ID, regId);
        editor.putInt(PROPERTY_APP_VERSION, appVersion);
        editor.commit();
    }

    private SharedPreferences getGCMPreferences(Context context) {
        // This sample app persists the registration ID in shared preferences, but
        // how you store the registration ID in your app is up to you.
        return mainViewContext.getSharedPreferences(MainActivity.class.getSimpleName(),
                Context.MODE_PRIVATE);
    }

    private static int getAppVersion(Context context) {
        try {
            PackageInfo packageInfo = context.getPackageManager()
                    .getPackageInfo(context.getPackageName(), 0);
            return packageInfo.versionCode;
        } catch (PackageManager.NameNotFoundException e) {
            // should never happen
            throw new RuntimeException("Could not get package name: " + e);
        }
    }

    private boolean checkPlayServices() {
        int resultCode = GooglePlayServicesUtil.isGooglePlayServicesAvailable(mainViewContext);
        if (resultCode != ConnectionResult.SUCCESS) {
            if (GooglePlayServicesUtil.isUserRecoverableError(resultCode)) {
                GooglePlayServicesUtil.getErrorDialog(resultCode, mainViewContext,
                        PLAY_SERVICES_RESOLUTION_REQUEST).show();
                Log.i(TAG, "This device is supported.");
            } else {
                Log.i(TAG, "This device is not supported.");
            }

            return false;
        }
        return true;
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
                            MovingImageSliderView defaultSliderView = new MovingImageSliderView(mainViewContext);
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
                        Intent intent = new Intent(mainViewContext, LoginActivity.class);
                        int x = toolbar.getWidth() -
                                mainViewContext.getResources().getDimensionPixelSize(R.dimen.overflow_x);
                        int y = mainViewContext.getResources().getDimensionPixelSize(R.dimen.overflow_y);
                        intent.putExtra(LoginActivity.RIPPLE_ORIGIN_X, x);
                        intent.putExtra(LoginActivity.RIPPLE_ORIGIN_Y, y);
                        mainViewContext.startActivityForResult(intent, LOGIN_REQUEST);
                    } else if (item.getItemId() == R.id.action_logout) {
                        mainInteractor.performLogout();
                        bus.post(new OnLogoutSuccessful());
                    }else if (item.getItemId() == R.id.action_refresh) {
                        bus.post(new StreamRefreshedEvent());
                    } else {
                        Intent intent = new Intent(mainViewContext, SettingsActivity.class);
                        mainViewContext.startActivity(intent);
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
