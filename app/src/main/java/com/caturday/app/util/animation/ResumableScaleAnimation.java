package com.caturday.app.util.animation;

import android.content.Context;
import android.util.AttributeSet;
import android.view.animation.ScaleAnimation;
import android.view.animation.Transformation;

/**
 * Created by Cat#2 on 08/04/15.
 */
public class ResumableScaleAnimation extends ScaleAnimation {
    private boolean mPaused;
    private long mElapsedAtPause;

    public ResumableScaleAnimation(float fromX, float toX, float fromY, float toY, int pivotXType, float pivotXValue, int pivotYType, float pivotYValue) {
        super(fromX, toX, fromY, toY, pivotXType, pivotXValue, pivotYType, pivotYValue);
    }

    public ResumableScaleAnimation(float fromX, float toX, float fromY, float toY, float pivotX, float pivotY) {
        super(fromX, toX, fromY, toY, pivotX, pivotY);
    }

    public ResumableScaleAnimation(float fromX, float toX, float fromY, float toY) {
        super(fromX, toX, fromY, toY);
    }

    public ResumableScaleAnimation(Context context, AttributeSet attrs) {
        super(context, attrs);
    }
        @Override
    public boolean getTransformation(long currentTime, Transformation outTransformation) {
        if(mPaused && mElapsedAtPause==0) {
            mElapsedAtPause = currentTime-getStartTime();
        }
        if(mPaused)
            setStartTime(currentTime-mElapsedAtPause);
        return super.getTransformation(currentTime, outTransformation);
    }

    public void pause() {
        mElapsedAtPause = 0;
        mPaused=true;
    }

    public void resume() {
        mPaused=false;
    }
}
