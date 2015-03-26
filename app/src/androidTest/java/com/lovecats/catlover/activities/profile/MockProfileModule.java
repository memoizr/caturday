package com.lovecats.catlover.activities.profile;

import android.content.Context;

import com.lovecats.catlover.AppModule;
import com.lovecats.catlover.data.DaoManager;
import com.lovecats.catlover.data.UserModel;
import com.lovecats.catlover.ui.activities.Profile.ProfileActivity;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module(
        injects = {
                ProfileActivity.class
        },
        addsTo = AppModule.class
)
public class MockProfileModule {
    private Context context;

    public MockProfileModule(Context context) {
        this.context = context;
    }

    @Provides @Singleton public UserModel provideUserModel() {
        DaoManager.DaoLoader(context);
        return new UserModel();
    }
}
