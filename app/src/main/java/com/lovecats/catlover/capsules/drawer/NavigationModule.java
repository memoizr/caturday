package com.lovecats.catlover.capsules.drawer;

import com.lovecats.catlover.AppModule;
import com.lovecats.catlover.capsules.drawer.interactor.NavigationInteractor;
import com.lovecats.catlover.capsules.drawer.interactor.NavigationInteractorImpl;
import com.lovecats.catlover.capsules.drawer.view.NavigationFragment;
import com.lovecats.catlover.capsules.drawer.view.NavigationView;
import com.lovecats.catlover.models.user.UserModule;
import com.lovecats.catlover.models.user.repository.UserRepository;

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
    private NavigationView navigationView;

    public NavigationModule(NavigationView navigationView) {
        this.navigationView = navigationView;
    }

    @Provides @Singleton NavigationInteractor provideNavigationInteractor(UserRepository userRepository) {
        return new NavigationInteractorImpl(userRepository);
    }

    @Provides
    @Singleton public NavigationView provideNavigationView(NavigationInteractor navigationInteractor) {
        return navigationView;
    }

    @Provides @Singleton
    public NavigationPresenter provideNavigationPresenter(NavigationView navigationView,
                                                          NavigationInteractor navigationInteractor) {
        return new NavigationPresenterImpl(navigationView, navigationInteractor);
    }
}
