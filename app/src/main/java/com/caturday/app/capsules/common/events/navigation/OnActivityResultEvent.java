package com.caturday.app.capsules.common.events.navigation;

import android.content.Intent;

import lombok.Value;

@Value
public class OnActivityResultEvent {

    private final Intent data;
    private final int resultCode;
    private final int requestCode;

    public OnActivityResultEvent(int requestCode, int resultCode, Intent data) {
        this.requestCode = requestCode;
        this.resultCode = resultCode;
        this.data = data;
    }
}
