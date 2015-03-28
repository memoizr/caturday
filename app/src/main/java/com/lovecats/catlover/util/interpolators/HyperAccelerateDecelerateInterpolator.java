package com.lovecats.catlover.util.interpolators;

import android.animation.TimeInterpolator;

/**
 * Created by user on 08/03/15.
 */
public class HyperAccelerateDecelerateInterpolator implements TimeInterpolator {
    @Override
    public float getInterpolation(float input) {
        return (float) (0.5 * Math.tanh(5 * input - 2.5) + 0.5);
    }
}
