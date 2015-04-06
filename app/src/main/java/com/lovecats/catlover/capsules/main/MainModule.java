package com.lovecats.catlover.capsules.main;

import android.content.Context;

import com.lovecats.catlover.AppModule;
import com.lovecats.catlover.capsules.favorites.FavoritesFragment;
import com.lovecats.catlover.capsules.dashboard.DashboardFragment;
import com.lovecats.catlover.capsules.drawer.NavigationFragment;
import com.lovecats.catlover.capsules.common.view.views.CollapsibleView;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module(
        injects = {
                MainActivity.class,

        },
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

    @Provides @Singleton public FavoritesFragment provideFavoritesFragment() {
        return new FavoritesFragment();
    }

}
