package com.lovecats.catlover.ui.common;

import android.support.v4.app.Fragment;

import com.github.ksoichiro.android.observablescrollview.ScrollState;

public interface ScrollEventListener {
    void onScrollChanged(int scrollY, boolean dragging);

    void onUpOrCancelMotionEvent(ScrollState scrollState);

    public void onScrollStateChanged(int scrollState);
}
