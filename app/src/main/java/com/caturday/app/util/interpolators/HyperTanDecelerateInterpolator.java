package com.caturday.app.util.interpolators;

import android.animation.TimeInterpolator;

public class HyperTanDecelerateInterpolator implements TimeInterpolator{
        @Override
        public float getInterpolation(float input) {
            return (float) Math.tanh(input*2.5);
        }
}
