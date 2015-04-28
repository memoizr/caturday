package com.lovecats.catlover.capsules.main;

import android.content.Context;

import com.lovecats.catlover.AppModule;
import com.lovecats.catlover.capsules.favorites.view.FavoritesFragment;
import com.lovecats.catlover.capsules.dashboard.DashboardFragment;
import com.lovecats.catlover.capsules.drawer.view.NavigationFragment;
import com.lovecats.catlover.capsules.common.view.views.CollapsibleView;
import com.lovecats.catlover.capsules.main.interactor.MainInteractor;
import com.lovecats.catlover.capsules.main.interactor.MainInteractorImpl;
import com.lovecats.catlover.capsules.main.presenter.MainPresenter;
import com.lovecats.catlover.capsules.main.presenter.MainPresenterImpl;
import com.lovecats.catlover.capsules.main.view.MainActivity;
import com.lovecats.catlover.models.catpost.repository.CatPostRepository;
import com.lovecats.catlover.models.user.UserModule;
import com.lovecats.catlover.models.user.repository.UserRepository;
import com.squareup.otto.Bus;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module(
        injects = MainActivity.class,
        includes = UserModule.class,
        addsTo = AppModule.class
)
public class MainModule {

    private Context context;

    public MainModule(Context context) {
        this.context = context;
    }

    @Provides @Singleton public MainInteractor provideMainInteractor(
            UserRepository userRepository,
            CatPostRepository catPostRepository) {
        return new MainInteractorImpl(userRepository, catPostRepository);
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
