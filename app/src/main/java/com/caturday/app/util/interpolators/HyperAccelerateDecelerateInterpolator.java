package com.caturday.app.util.interpolators;

import android.animation.TimeInterpolator;
import android.view.animation.PathInterpolator;

public class HyperAccelerateDecelerateInterpolator implements TimeInterpolator {
    @Override
    public float getInterpolation(float input) {
        return (float) (0.5 * Math.tanh(5 * input - 2.5) + 0.5);
    }

    public static TimeInterpolator getInterpolator() {
        return new PathInterpolator(0.7f, 0f, 0.3f, 1f);
    }
}
