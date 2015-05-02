package com.caturday.app.capsules.profile.info;

import com.caturday.app.AppModule;
import com.caturday.app.models.user.UserModule;
import com.squareup.otto.Bus;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module(
        injects = {
                UserInfoFragment.class
        },
        includes = {
                UserModule.class
        },
        addsTo = AppModule.class
)
public class UserInfoModule {

    private UserInfoView userInfoView;

    public UserInfoModule(UserInfoView userInfoView) {
        this.userInfoView = userInfoView;
    }

    @Provides @Singleton public UserInfoView provideUserInfoView() {
        return userInfoView;
    }

    @Provides @Singleton public UserInfoPresenter provideUserInfoPresenter(
            UserInfoView userInfoView, Bus bus) {
        return new UserInfoPresenterImpl(userInfoView, bus);
    }
}
