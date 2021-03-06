package com.caturday.app.capsules.profile.following.view;

import android.content.Context;

import com.google.gson.JsonArray;
import com.caturday.app.capsules.common.events.UserAvailableEvent;
import com.caturday.app.models.user.UserEntity;
import com.squareup.otto.Bus;
import com.squareup.otto.Subscribe;

public class FollowingPresenterImpl implements FollowingPresenter {

    private final FollowingView followingView;
    private final Bus bus;
    private int type;
    private boolean registered;

    public FollowingPresenterImpl(FollowingView followingView, Bus bus) {
        this.followingView = followingView;
        this.bus = bus;
    }

    private void setRegistered(boolean registered) {
        this.registered = registered;
    }

    // TODO show followers

    @Subscribe
    public void onUserAvailable(UserAvailableEvent userAvailableEvent) {
//        UserEntity userEntity = userAvailableEvent.getUserEntity();
//        List<UserEntity> userEntities = GsonConverter.fromJsonArrayToTypeArray(
//                getUserEntities(userEntity), UserEntity.class);
//
//        followingView.getAdapter().setUserEntities(userEntities);
    }

    private JsonArray getUserEntities(UserEntity userEntity) {
        JsonArray json = new JsonArray();
//        if (type == ProfilePageAdapter.ProfileSections.SECTION_FOLLOWERS.ordinal()) {
//            json = userEntity.getFollowers();
//        } else if (type == ProfilePageAdapter.ProfileSections.SECTION_FOLLOWING.ordinal()) {
//            json = userEntity.getFollowing();
//        }
        return json;
    }

    @Override
    public void viewCreated(Context context, int type) {
        followingView.initRecyclerView();
        this.type = type;
        if (!registered) {
            bus.register(this);
            setRegistered(true);
        }
    }
}
