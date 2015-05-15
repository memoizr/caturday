package com.caturday.app.capsules.common.events;

import lombok.Getter;

public class OnPagerScrolledEvent {

        @Getter private final int position;

        public OnPagerScrolledEvent(int position) {
            this.position = position;
        }
}
