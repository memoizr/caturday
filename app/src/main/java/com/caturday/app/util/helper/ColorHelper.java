package com.caturday.app.util.helper;

import android.graphics.Color;

public class ColorHelper {

    public static int interpolateColor(int fromColor, int toColor, float fraction) {
        int[] fromArgb = getArgbFromColor(fromColor);
        int[] toArgb = getArgbFromColor(toColor);
        int[] returnArgb = new int[4];

        for (int i = 0; i < 4; i++) {
            returnArgb[i] = (int) MathHelper.interpolate(fromArgb[i], toArgb[i], fraction);
        }

        return Color.argb(returnArgb[0], returnArgb[1], returnArgb[2], returnArgb[3]);
    }

    private static int[] getArgbFromColor(int color) {
        int red = (color >> 16) & 0xFF;
        int green = (color >> 8) & 0xFF;
        int blue = (color >> 0) & 0xFF;
        int alpha = (color >> 24) & 0xFF;

        return new int[]{alpha, red, green, blue};
    }
}
