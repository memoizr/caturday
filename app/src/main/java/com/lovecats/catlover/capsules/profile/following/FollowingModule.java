package com.lovecats.catlover.capsules.profile.following;

import com.lovecats.catlover.AppModule;
import com.lovecats.catlover.capsules.profile.info.UserInfoFragment;
import com.lovecats.catlover.capsules.profile.info.UserInfoPresenter;
import com.lovecats.catlover.capsules.profile.info.UserInfoPresenterImpl;
import com.lovecats.catlover.capsules.profile.info.UserInfoView;
import com.lovecats.catlover.models.user.UserModule;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module(
        injects = {
                FollowingFragment.class
        },
        addsTo = AppModule.class
)
public class FollowingModule {

    private FollowingView followingView;

    public FollowingModule(FollowingView followingView) {
        this.followingView = followingView;
    }

    @Provides @Singleton public FollowingView provideFollowingView() {
        return followingView;
    }

    @Provides @Singleton public FollowingPresenter provideFollowingPresenter(
            FollowingView followingView) {
        return new FollowingPresenterImpl(followingView);
    }
}
