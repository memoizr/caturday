package com.caturday.app.capsules.common.events;

import lombok.Value;

@Value
public class OnPostCreatedEvent {

    private final String serverId;

    public OnPostCreatedEvent(String serverId) {
        this.serverId = serverId;
    }
}
