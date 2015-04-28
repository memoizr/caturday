package com.lovecats.catlover.capsules.profile.following.view;

import android.content.Context;

import com.lovecats.catlover.capsules.common.events.UserAvailableEvent;
import com.lovecats.catlover.models.user.UserEntity;
import com.lovecats.catlover.util.data.GsonConverter;
import com.squareup.otto.Bus;
import com.squareup.otto.Subscribe;

import java.util.List;

public class FollowingPresenterImpl implements FollowingPresenter {

    private final FollowingView followingView;
    private final Bus bus;

    public FollowingPresenterImpl(FollowingView followingView, Bus bus) {
        this.followingView = followingView;
        this.bus = bus;

        bus.register(this);
    }

    @Subscribe
    public void onUserAvailable(UserAvailableEvent userAvailableEvent) {
        System.out.println("available now!!!!!!!!!1111");
        UserEntity userEntity = userAvailableEvent.getUserEntity();
        List<UserEntity> userEntities = GsonConverter.fromJsonArrayToTypeArray(
                userEntity.getFollowing(), UserEntity.class);
        followingView.getAdapter().setUserEntities(userEntities);
    }

    @Override
    public void viewCreated(Context context) {
        followingView.initRecyclerView();
    }
}
