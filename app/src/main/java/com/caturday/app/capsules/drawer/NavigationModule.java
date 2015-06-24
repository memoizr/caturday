package com.caturday.app.capsules.drawer;

import com.caturday.app.AppModule;
import com.caturday.app.capsules.drawer.interactor.NavigationInteractor;
import com.caturday.app.capsules.drawer.interactor.NavigationInteractorImpl;
import com.caturday.app.capsules.drawer.view.NavigationFragment;
import com.caturday.app.capsules.drawer.view.NavigationPresenter;
import com.caturday.app.capsules.drawer.view.NavigationPresenterImpl;
import com.caturday.app.models.user.UserModule;
import com.caturday.app.models.user.repository.UserRepository;
import com.squareup.otto.Bus;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module(
        injects = {
                NavigationFragment.class
        },
        includes = UserModule.class,
        addsTo = AppModule.class
)
public class NavigationModule {
    private NavigationPresenter.NavigationView navigationView;

    public NavigationModule(NavigationPresenter.NavigationView navigationView) {
        this.navigationView = navigationView;
    }

    @Provides @Singleton
    NavigationInteractor provideNavigationInteractor(UserRepository userRepository) {
        return new NavigationInteractorImpl(userRepository);
    }

    @Provides @Singleton
    public NavigationPresenter.NavigationView provideNavigationView(NavigationInteractor navigationInteractor) {
        return navigationView;
    }

    @Provides @Singleton
    public NavigationPresenter provideNavigationPresenter(NavigationPresenter.NavigationView navigationView,
                                                          NavigationInteractor navigationInteractor,
                                                          Bus bus) {
        return new NavigationPresenterImpl(navigationView, navigationInteractor, bus);
    }
}
