package com.caturday.app.capsules.dashboard;


import com.caturday.app.AppModule;
import com.caturday.app.capsules.dashboard.interactor.DashboardInteractor;
import com.caturday.app.capsules.dashboard.interactor.DashboardInteractorImpl;
import com.caturday.app.models.user.UserModule;
import com.caturday.app.models.user.repository.UserRepository;
import com.squareup.otto.Bus;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module(
        injects = {
                DashboardFragment.class
        },
        includes = UserModule.class,
        addsTo = AppModule.class
)
public class DashboardModule {
    private DashboardView dashboardView;

    public DashboardModule(DashboardView dashboardView) {
        this.dashboardView = dashboardView;
    }

    @Provides
    @Singleton
    public DashboardView provideDashboardView() {
        return dashboardView;
    }

    @Provides
    @Singleton
    public DashboardInteractor provideDashboardInteractor(UserRepository userRepository) {
        return new DashboardInteractorImpl(userRepository);
    }

    @Provides @Singleton
    public DashboardPresenter provideDashboardPresenter(DashboardView dashboardView,
                                                        DashboardInteractor interactor,
                                                        Bus eventBus) {
        return new DashboardPresenterImpl(dashboardView, interactor, eventBus);
    }
}
