package com.lovecats.catlover.ui.activities.profile;


import android.content.Context;

import com.lovecats.catlover.AppModule;
import com.lovecats.catlover.data.DaoManager;
import com.lovecats.catlover.data.UserModel;

import static org.mockito.Mockito.*;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module(
        injects = {
                MockProfileActivity.class
        },
        addsTo = AppModule.class
)
public class MockProfileModule {
    private Context context;

    public MockProfileModule(Context context) {
        this.context = context;
    }

    @Provides @Singleton
    public UserModel provideUserModel() {
        DaoManager.DaoLoader(context);
        UserModel mock = mock(UserModel.class);

        when(mock.userLoggedIn()).thenReturn(true);
        return mock;
    }
}
