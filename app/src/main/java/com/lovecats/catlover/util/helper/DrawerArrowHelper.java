package com.lovecats.catlover.util.helper;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;

import com.lovecats.catlover.util.interpolators.HyperAccelerateDecelerateInterpolator;

public class DrawerArrowHelper {
    public static void toggleArrow(boolean toggle, ActionBarDrawerToggle drawerToggle, DrawerLayout drawerLayout) {
        final ActionBarDrawerToggle mDrawerToggle = drawerToggle;
        final DrawerLayout mDrawerLayout = drawerLayout;

        ValueAnimator va;
        if (toggle) {
            va = new ObjectAnimator().ofFloat(0, 1);
            va.setDuration(500);
        } else {
            va = new ObjectAnimator().ofFloat(1, 0);
            va.setDuration(400);
        }
        va.setInterpolator(new HyperAccelerateDecelerateInterpolator());
        va.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                mDrawerToggle.onDrawerSlide(mDrawerLayout, Float.parseFloat(animation.getAnimatedValue().toString()));
            }
        });
        va.start();
    }
}
