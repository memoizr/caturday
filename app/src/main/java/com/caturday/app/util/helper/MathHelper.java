package com.caturday.app.util.helper;

public class MathHelper {

    public static float interpolate(float from, float to, float fraction) {
        return ((to - from) * fraction) + from;
    }
}
