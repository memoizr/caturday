package com.lovecats.catlover.capsules.profile.info;

import com.lovecats.catlover.AppModule;
import com.lovecats.catlover.capsules.profile.interactor.ProfileInteractor;
import com.lovecats.catlover.capsules.profile.interactor.ProfileInteractorImpl;
import com.lovecats.catlover.capsules.profile.view.ProfileActivity;
import com.lovecats.catlover.capsules.profile.view.ProfilePresenter;
import com.lovecats.catlover.capsules.profile.view.ProfilePresenterImpl;
import com.lovecats.catlover.capsules.profile.view.ProfileView;
import com.lovecats.catlover.models.user.UserModule;
import com.lovecats.catlover.models.user.repository.UserRepository;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module(
        injects = {
                UserInfoFragment.class
        },
        includes = UserModule.class,
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
            UserInfoView userInfoView) {
        return new UserInfoPresenterImpl(userInfoView);
    }
}
