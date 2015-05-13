package com.caturday.app.util.interpolators;

import android.animation.TimeInterpolator;
import android.view.animation.PathInterpolator;

public class HyperTanAccelerateInterpolator implements TimeInterpolator{
    @Override
    public float getInterpolation(float input) {
        return (float) Math.tanh(input*2.5 - 2.5) + 1;
    }

    public static TimeInterpolator getInterpolator() {
        return new PathInterpolator(0.5f, 0f, 0.75f, 0.25f);
    }
}
