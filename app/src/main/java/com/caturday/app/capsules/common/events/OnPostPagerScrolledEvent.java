package com.caturday.app.capsules.common.events;

import lombok.Getter;

public class OnPostPagerScrolledEvent {
    @Getter private final int position;
    @Getter private int offset;

    public OnPostPagerScrolledEvent(int position, int offset) {
        this.position = position;
        this.offset = offset;
    }
}
