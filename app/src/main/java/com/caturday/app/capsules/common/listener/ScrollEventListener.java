package com.caturday.app.capsules.common.listener;

import com.github.ksoichiro.android.observablescrollview.ScrollState;

public interface ScrollEventListener {
    void onScrollChanged(int scrollY, boolean dragging);

    void onUpOrCancelMotionEvent(ScrollState scrollState);

    void onScrollStateChanged(int scrollState);
}
