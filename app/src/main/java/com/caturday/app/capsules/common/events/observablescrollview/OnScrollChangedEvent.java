package com.caturday.app.capsules.common.events.observablescrollview;

import lombok.Getter;

public class OnScrollChangedEvent {

    @Getter private final boolean dragging;
    @Getter private final boolean firstScroll;
    @Getter private final int scrollY;

    public OnScrollChangedEvent(int scrollY, boolean firstScroll, boolean dragging) {
        this.scrollY = scrollY;
        this.firstScroll = firstScroll;
        this.dragging = dragging;
    }
}
