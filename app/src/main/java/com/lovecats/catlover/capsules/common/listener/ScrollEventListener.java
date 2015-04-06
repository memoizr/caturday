package com.lovecats.catlover.capsules.common.listener;

import com.github.ksoichiro.android.observablescrollview.ScrollState;

public interface ScrollEventListener {
    void onScrollChanged(int scrollY, boolean dragging);

    void onUpOrCancelMotionEvent(ScrollState scrollState);

    public void onScrollStateChanged(int scrollState);
}
