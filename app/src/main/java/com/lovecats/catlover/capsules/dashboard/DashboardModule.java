package com.lovecats.catlover.capsules.dashboard;


import com.lovecats.catlover.AppModule;
import com.squareup.otto.Bus;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module(
        injects = {
                DashboardFragment.class
        },
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

    @Provides @Singleton
    public DashboardPresenter provideDashboardPresenter(DashboardView dashboardView, Bus eventBus) {
        return new DashboardPresenterImpl(dashboardView, eventBus);
    }
}
