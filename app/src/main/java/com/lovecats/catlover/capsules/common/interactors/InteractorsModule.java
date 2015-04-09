package com.lovecats.catlover.capsules.common.interactors;

import com.lovecats.catlover.capsules.drawer.interactor.NavigationInteractor;
import com.lovecats.catlover.capsules.drawer.interactor.NavigationInteractorImpl;
import com.lovecats.catlover.capsules.profile.interactor.ProfileInteractor;
import com.lovecats.catlover.capsules.profile.interactor.ProfileInteractorImpl;
import com.lovecats.catlover.models.user.UserModule;
import com.lovecats.catlover.models.user.db.UserORM;
import com.lovecats.catlover.models.user.repository.UserRepository;
import com.lovecats.catlover.models.user.repository.UserRepositoryImpl;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import greendao.DaoSession;

@Module(
        complete = false,
        library = true,
        includes = UserModule.class
)
public class InteractorsModule {

    @Provides
    @Singleton
    public ProfileInteractor provideProfileInteractor(UserRepository userRepository) {
        return new ProfileInteractorImpl(userRepository);
    }

    @Provides
    @Singleton
    public NavigationInteractor provideNavigationInteractor() {
        return new NavigationInteractorImpl();
    }
}
