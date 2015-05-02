package com.caturday.app.capsules.common.events;

import lombok.Getter;

public class OnPageScrolledEvent {

        @Getter private final int position;
        @Getter private int offset;

        public OnPageScrolledEvent(int position, int offset) {
            this.position = position;
            this.offset = offset;
        }
}
