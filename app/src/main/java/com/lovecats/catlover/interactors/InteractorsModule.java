package com.lovecats.catlover.interactors;

import android.content.Context;

import com.lovecats.catlover.data.DaoManager;
import com.lovecats.catlover.data.UserModel;

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
        return new ProfileInteractorConcrete(userModel);
    }
}
