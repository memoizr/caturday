package com.caturday.app.capsules.profile;

import com.caturday.app.AppModule;
import com.caturday.app.capsules.profile.interactor.ProfileInteractor;
import com.caturday.app.capsules.profile.interactor.ProfileInteractorImpl;
import com.caturday.app.capsules.profile.view.ProfileActivity;
import com.caturday.app.capsules.profile.view.ProfilePresenter;
import com.caturday.app.capsules.profile.view.ProfilePresenterImpl;
import com.caturday.app.capsules.profile.view.ProfileView;
import com.caturday.app.models.user.UserModule;
import com.caturday.app.models.user.repository.UserRepository;
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

    private static ProfileModule profileModule;

    private ProfileModule(ProfileView profileView) {
        this.profileView = profileView;
    }

    public static ProfileModule getInstance(ProfileView profileView) {
        if (profileModule == null)
            profileModule = new ProfileModule(profileView);
        return profileModule;
    }

    @Provides @Singleton
    public ProfileInteractor provideProfileInteractor(UserRepository userRepository) {
        return new ProfileInteractorImpl(userRepository);
    }

    @Provides @Singleton
    public ProfileView provideProfileView() {
        return profileView;
    }

    @Provides
    public ProfilePresenter provideProfilePresenter(
            ProfileView profileView,
            ProfileInteractor profileInteractor,
            Bus bus) {

        return new ProfilePresenterImpl(profileView, profileInteractor, bus);
    }
}
