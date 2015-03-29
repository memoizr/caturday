package com.lovecats.catlover.ui.drawer;

import com.lovecats.catlover.AppModule;
import com.lovecats.catlover.interactors.navigation.NavigationInteractor;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module(
        injects = {
                NavigationFragment.class
        },
        addsTo = AppModule.class
)
public class NavigationModule {
    private NavigationView navigationView;

    public NavigationModule(NavigationView navigationView) {
        this.navigationView = navigationView;
    }

    @Provides
    @Singleton public NavigationView provideNavigationView() {
        return navigationView;
    }

    @Provides @Singleton
    public NavigationPresenter provideNavigationPresenter(NavigationView navigationView,
                                                          NavigationInteractor navigationInteractor) {
        return new NavigationPresenterImpl(navigationView, navigationInteractor);
    }
}
