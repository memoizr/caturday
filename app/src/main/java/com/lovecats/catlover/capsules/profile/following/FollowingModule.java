package com.lovecats.catlover.capsules.profile.following;

import com.lovecats.catlover.AppModule;
import com.lovecats.catlover.capsules.profile.following.view.FollowingFragment;
import com.lovecats.catlover.capsules.profile.following.view.FollowingPresenter;
import com.lovecats.catlover.capsules.profile.following.view.FollowingPresenterImpl;
import com.lovecats.catlover.capsules.profile.following.view.FollowingView;
import com.squareup.otto.Bus;

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

    @Provides public FollowingPresenter provideFollowingPresenter(
            FollowingView followingView,
            Bus bus) {

        return new FollowingPresenterImpl(followingView, bus);
    }
}
