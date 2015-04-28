package com.lovecats.catlover.capsules.common.events;

import com.lovecats.catlover.models.user.UserEntity;

import lombok.Getter;

public class UserAvailableEvent {

    @Getter private final UserEntity userEntity;

    public UserAvailableEvent(UserEntity userEntity) {
        this.userEntity = userEntity;
    }
}
