package com.caturday.app.capsules.common.events;

import com.caturday.app.models.user.UserEntity;

import lombok.Getter;

public class UserAvailableEvent {

    @Getter private final UserEntity userEntity;
    @Getter private final boolean isCurrentUser;

    public UserAvailableEvent(UserEntity userEntity, boolean isCurrentUser) {
        this.userEntity = userEntity;
        this.isCurrentUser = isCurrentUser;
    }
}
