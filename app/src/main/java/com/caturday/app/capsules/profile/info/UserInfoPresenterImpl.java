package com.caturday.app.capsules.profile.info;

import com.caturday.app.capsules.common.events.UserAvailableEvent;
import com.caturday.app.models.user.UserEntity;
import com.squareup.otto.Bus;
import com.squareup.otto.Subscribe;

public class UserInfoPresenterImpl implements UserInfoPresenter{

    private final UserInfoView userInfoView;
    private final Bus bus;

    public UserInfoPresenterImpl(UserInfoView userInfoView, Bus bus) {
        this.userInfoView = userInfoView;
        this.bus = bus;
    }

    @Override
    public void createView() {
        bus.register(this);
    }

    @Override
    public void destroyView() {
        bus.unregister(this);
    }

    @Subscribe
    public void userAvailable(UserAvailableEvent userLoadedEvent) {
        UserEntity userEntity = userLoadedEvent.getUserEntity();
        userInfoView.setEmail(userEntity.getEmail());
        userInfoView.setUsername(userEntity.getUsername());
        userInfoView.setDescription(userEntity.getDescription());
    }
}
