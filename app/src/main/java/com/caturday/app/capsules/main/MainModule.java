package com.caturday.app.capsules.main;

import android.content.Context;

import com.caturday.app.AppModule;
import com.caturday.app.capsules.dashboard.DashboardFragment;
import com.caturday.app.capsules.drawer.view.NavigationFragment;
import com.caturday.app.capsules.common.view.views.CollapsibleView;
import com.caturday.app.capsules.main.interactor.MainInteractor;
import com.caturday.app.capsules.main.interactor.MainInteractorImpl;
import com.caturday.app.capsules.main.presenter.MainPresenter;
import com.caturday.app.capsules.main.presenter.MainPresenterImpl;
import com.caturday.app.capsules.main.view.MainActivity;
import com.caturday.app.models.catpost.repository.CatPostRepository;
import com.caturday.app.models.gcm.GcmModule;
import com.caturday.app.models.gcm.repository.GcmRepository;
import com.caturday.app.models.user.UserModule;
import com.caturday.app.models.user.repository.UserRepository;
import com.squareup.otto.Bus;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module(
        injects = MainActivity.class,
        includes = {
                UserModule.class,
                GcmModule.class
        },
        addsTo = AppModule.class
)
public class MainModule {

    private Context context;

    public MainModule(Context context) {
        this.context = context;
    }

    @Provides @Singleton public MainInteractor provideMainInteractor(
            UserRepository userRepository,
            CatPostRepository catPostRepository,
            GcmRepository gcmRepository) {
        return new MainInteractorImpl(userRepository, catPostRepository, gcmRepository);
    }

    @Provides @Singleton public MainPresenter provideMainPresenter(MainInteractor mainInteractor,
                                                                   Bus bus) {
       return new MainPresenterImpl((MainActivity) context, mainInteractor, bus);
    }

    @Provides @Singleton public CollapsibleView provideCollapsibleView() {
        return new CollapsibleView(context);
    }

    @Provides public DashboardFragment provideDashboardFragment() {
        return new DashboardFragment();
    }

    @Provides @Singleton public NavigationFragment provideNavigationFragment() {
        return new NavigationFragment();
    }
}
