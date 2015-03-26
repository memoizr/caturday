package com.lovecats.catlover.ui.activities;

import android.content.Context;

import com.lovecats.catlover.AppModule;
import com.lovecats.catlover.data.DaoManager;
import com.lovecats.catlover.data.UserModel;
import com.lovecats.catlover.ui.activities.Profile.ProfileActivity;
import com.lovecats.catlover.ui.fragments.DashboardFragment;
import com.lovecats.catlover.ui.fragments.NavigationFragment;
import com.lovecats.catlover.ui.views.CollapsibleView;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module(
        injects = {
                MainActivity.class,
                ProfileActivity.class

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

    @Provides @Singleton public UserModel provideUserModel() {
        DaoManager.DaoLoader(context);
        return new UserModel();
    }
}
