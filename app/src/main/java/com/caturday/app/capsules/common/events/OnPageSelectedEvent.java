package com.caturday.app.capsules.common.events;

import lombok.Getter;

public class OnPageSelectedEvent {

    @Getter private final int position;

    public OnPageSelectedEvent(int position) {
        this.position = position;
    }


}
