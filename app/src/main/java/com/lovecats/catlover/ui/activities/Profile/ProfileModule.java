package com.lovecats.catlover.ui.activities.Profile;

import android.content.Context;

import com.lovecats.catlover.AppModule;
import com.lovecats.catlover.data.DaoManager;
import com.lovecats.catlover.data.UserModel;
import com.lovecats.catlover.ui.views.CollapsibleView;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module(
        injects = {
                ProfileActivity.class
        },
        addsTo = AppModule.class
)
public class ProfileModule {
    private Context context;

    public ProfileModule(Context context) {
        this.context = context;
    }

    @Provides @Singleton public UserModel provideUserModel() {
        DaoManager.DaoLoader(context);
        return new UserModel();
    }
}
