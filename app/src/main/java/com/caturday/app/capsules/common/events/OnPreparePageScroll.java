package com.caturday.app.capsules.common.events;

import lombok.Getter;

public class OnPreparePageScroll {

    @Getter private final int targetScroll;

    @Getter private final int sourcePagePosition;

    public OnPreparePageScroll(int targetScroll, int sourcePagePosition) {
        this.targetScroll = targetScroll;
        this.sourcePagePosition = sourcePagePosition;
    }
}
