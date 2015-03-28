package com.lovecats.catlover.ui.profile;

import com.lovecats.catlover.AppModule;
import com.lovecats.catlover.interactors.ProfileInteractor;

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
    private ProfileView profileView;

    public ProfileModule(ProfileView profileView) {
        this.profileView = profileView;
    }

    @Provides @Singleton public ProfileView provideProfileView() {
        return profileView;
    }

    @Provides @Singleton public ProfilePresenter provideProfilePresenter(ProfileView profileView, ProfileInteractor profileInteractor) {
        return new ProfilePresenterConcrete(profileView, profileInteractor);
    }
}
