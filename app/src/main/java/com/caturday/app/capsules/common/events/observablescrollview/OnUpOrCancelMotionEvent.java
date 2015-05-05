package com.caturday.app.capsules.common.events.observablescrollview;

import com.github.ksoichiro.android.observablescrollview.ScrollState;

import lombok.Value;

@Value
public class OnUpOrCancelMotionEvent {

    private final ScrollState scrollState;

    public OnUpOrCancelMotionEvent(ScrollState scrollState) {
        this.scrollState = scrollState;
    }
}
