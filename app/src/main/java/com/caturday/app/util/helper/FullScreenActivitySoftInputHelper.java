package com.caturday.app.util.helper;

import android.app.Activity;
import android.graphics.Rect;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.FrameLayout;

public class FullScreenActivitySoftInputHelper {

    public interface VisibleSizeChangeListener {
        void onVisibleSizeChanged(int heightDifference);
    }

    private VisibleSizeChangeListener mVisibleSizeChangeListener;

    public static void assistActivity (Activity activity, VisibleSizeChangeListener visibleSizeChangeListener) {
        new FullScreenActivitySoftInputHelper(activity, visibleSizeChangeListener);
    }

    private View mChildOfContent;
    private int usableHeightPrevious;

    private FullScreenActivitySoftInputHelper(Activity activity, VisibleSizeChangeListener visibleSizeChangeListener) {
        mVisibleSizeChangeListener = visibleSizeChangeListener;
        FrameLayout content = (FrameLayout) activity.findViewById(android.R.id.content);
        mChildOfContent = content.getChildAt(0);
        mChildOfContent.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            public void onGlobalLayout() {
               mVisibleSizeChangeListener.onVisibleSizeChanged(computeHeightDifference());
            }
        });
    }

    private int computeHeightDifference() {
        int usableHeightNow = computeUsableHeight();
        int heightDifference = usableHeightNow - usableHeightPrevious;
        usableHeightPrevious = usableHeightNow;
        return heightDifference;
    }

    private int computeUsableHeight() {
        Rect r = new Rect();
        mChildOfContent.getWindowVisibleDisplayFrame(r);
        return (r.bottom);
    }

}
