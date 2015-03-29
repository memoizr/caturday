package com.lovecats.catlover.interactors;

import com.lovecats.catlover.data.UserModel;
import com.lovecats.catlover.interactors.navigation.NavigationInteractor;
import com.lovecats.catlover.interactors.navigation.NavigationInteractorImpl;
import com.lovecats.catlover.interactors.profile.ProfileInteractor;
import com.lovecats.catlover.interactors.profile.ProfileInteractorImpl;

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
