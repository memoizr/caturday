package com.caturday.app.capsules.profile.following;

import com.caturday.app.AppModule;
import com.caturday.app.capsules.profile.following.view.FollowingFragment;
import com.caturday.app.capsules.profile.following.view.FollowingPresenter;
import com.caturday.app.capsules.profile.following.view.FollowingPresenterImpl;
import com.caturday.app.capsules.profile.following.view.FollowingView;
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
