package com.caturday.app.util.interpolators;

import android.animation.TimeInterpolator;
import android.view.animation.PathInterpolator;

public class HyperTanDecelerateInterpolator implements TimeInterpolator{
    @Override
    public float getInterpolation(float input) {
        return (float) Math.tanh(input*2.5);
    }

    public static TimeInterpolator getInterpolator() {
        return new PathInterpolator(0.25f, 0.75f, 0.5f, 1f);
    }
}
