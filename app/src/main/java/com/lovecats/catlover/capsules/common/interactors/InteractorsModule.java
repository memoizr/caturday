package com.lovecats.catlover.capsules.common.interactors;

import com.lovecats.catlover.data.user.UserModel;
import com.lovecats.catlover.capsules.drawer.interactor.NavigationInteractor;
import com.lovecats.catlover.capsules.drawer.interactor.NavigationInteractorImpl;
import com.lovecats.catlover.capsules.profile.interactor.ProfileInteractor;
import com.lovecats.catlover.capsules.profile.interactor.ProfileInteractorImpl;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module(
        complete = false,
        library = true
)
public class InteractorsModule {
//    private Context mContext;
//
//    public InteractorsModule(Context context) {
//        this.mContext = context;
//    }

    @Provides @Singleton public UserModel provideUserModel() {
//        DaoManager.DaoLoader(mContext);
        return new UserModel();
    }

    @Provides @Singleton public ProfileInteractor provideProfileInteractor(UserModel userModel) {
        return new ProfileInteractorImpl(userModel);
    }

    @Provides @Singleton public NavigationInteractor provideNavigationInteractor() {
        return new NavigationInteractorImpl();
    }
}
