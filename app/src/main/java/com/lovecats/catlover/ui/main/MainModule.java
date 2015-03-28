package com.lovecats.catlover.ui.main;

import android.content.Context;

import com.lovecats.catlover.AppModule;
import com.lovecats.catlover.data.DaoManager;
import com.lovecats.catlover.data.UserModel;
import com.lovecats.catlover.ui.profile.ProfileActivity;
import com.lovecats.catlover.ui.dashboard.DashboardFragment;
import com.lovecats.catlover.ui.drawer.NavigationFragment;
import com.lovecats.catlover.ui.views.CollapsibleView;

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

//    @Provides @Singleton public UserModel provideUserModel() {
//        DaoManager.DaoLoader(context);
//        return new UserModel();
//    }
}
