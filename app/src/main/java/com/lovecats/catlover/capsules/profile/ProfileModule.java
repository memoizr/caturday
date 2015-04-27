package com.lovecats.catlover.capsules.profile;

import com.lovecats.catlover.AppModule;
import com.lovecats.catlover.capsules.profile.interactor.ProfileInteractor;
import com.lovecats.catlover.capsules.profile.interactor.ProfileInteractorImpl;
import com.lovecats.catlover.capsules.profile.view.ProfileActivity;
import com.lovecats.catlover.capsules.profile.view.ProfilePresenter;
import com.lovecats.catlover.capsules.profile.view.ProfilePresenterImpl;
import com.lovecats.catlover.capsules.profile.view.ProfileView;
import com.lovecats.catlover.models.user.UserModule;
import com.lovecats.catlover.models.user.repository.UserRepository;
import com.squareup.otto.Bus;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module(
        injects = {
                ProfileActivity.class
        },
        includes = UserModule.class,
        addsTo = AppModule.class
)
public class ProfileModule {

    private ProfileView profileView;

    public ProfileModule(ProfileView profileView) {
        this.profileView = profileView;
    }

    @Provides @Singleton public ProfileInteractor provideProfileInteractor(UserRepository userRepository) {
        return new ProfileInteractorImpl(userRepository);
    }

    @Provides @Singleton public ProfileView provideProfileView() {
        return profileView;
    }

    @Provides @Singleton public ProfilePresenter provideProfilePresenter(
            ProfileView profileView,
            ProfileInteractor profileInteractor,
            Bus bus) {
        return new ProfilePresenterImpl(profileView, profileInteractor, bus);
    }
}
