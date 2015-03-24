package com.lovecats.catlover.ui.activities;

import android.app.Activity;
import android.content.Context;
import android.support.v7.app.ActionBarDrawerToggle;

import com.lovecats.catlover.AppModule;
import com.lovecats.catlover.ui.fragments.DashboardFragment;
import com.lovecats.catlover.ui.fragments.NavigationFragment;
import com.lovecats.catlover.ui.views.CollapsibleView;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import lombok.core.Main;

/**
 * Created by user on 24/03/15.
 */
@Module(
        injects = MainActivity.class,
        addsTo = AppModule.class
)
public class MainModule {
    private Context context;

    public MainModule(Context context) {
        this.context = context;
    }

    @Provides @Singleton public CollapsibleView provideCollapsibleView() {
        return new CollapsibleView(context);
    }

    @Provides @Singleton public DashboardFragment provideDashboardFragment() {
        return new DashboardFragment();
    }

    @Provides @Singleton public NavigationFragment provideNavigationFragment() {
        return new NavigationFragment();
    }
}
