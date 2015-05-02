package com.caturday.app.capsules.common.events;

import com.caturday.app.models.user.UserEntity;

import lombok.Getter;

public class UserAvailableEvent {

    @Getter private final UserEntity userEntity;

    public UserAvailableEvent(UserEntity userEntity) {
        this.userEntity = userEntity;
    }
}
