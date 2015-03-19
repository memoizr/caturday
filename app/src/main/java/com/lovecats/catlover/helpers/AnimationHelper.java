package com.lovecats.catlover.helpers;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.PaintDrawable;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.view.ViewGroup;
import android.widget.TextView;

import com.lovecats.catlover.LoginActivity;
import com.lovecats.catlover.util.HyperTanAccelerateInterpolator;
import com.lovecats.catlover.util.HyperTanDecelerateInterpolator;

public class AnimationHelper {
    private static final int GLIDE_DISTANCE = 400;
    private static final int MEDIUM_GLIDE_DISTANCE = 200;
    private static final int SMALL_GLIDE_DISTANCE = 80;

    public static void glideUp(ViewGroup viewGroup) {
        int count = viewGroup.getChildCount();
        for (int i = 0; i < count; i++) {
            View view = viewGroup.getChildAt(i);
            view.setTranslationY(GLIDE_DISTANCE);
            view.setAlpha(0f);
            view.animate()
                    .translationYBy(-GLIDE_DISTANCE)
                    .alpha(1f)
                    .setDuration(300)
                    .setInterpolator(new HyperTanDecelerateInterpolator())
                    .setStartDelay(i * 64 + 600)
                    .start();
        }
    }


    public static void zoomOut(View view) {
        view.animate()
                .scaleX(0f)
                .scaleY(0f)
                .setDuration(300)
                .setInterpolator(new HyperTanAccelerateInterpolator())
                .start();
    }

    public static void zoomIn(View view) {
        view.setVisibility(View.VISIBLE);
        view.setScaleY(0f);
        view.setScaleX(0f);
        view.animate()
                .scaleX(1f)
                .scaleY(1f)
                .setDuration(300)
                .setInterpolator(new HyperTanDecelerateInterpolator())
                .start();
    }

    public static void glideDown(ViewGroup viewGroup) {
        int count = viewGroup.getChildCount();
        for (int i = 0; i < count; i++) {
            View view = viewGroup.getChildAt(i);
            view.animate()
                    .translationYBy(GLIDE_DISTANCE)
                    .alpha(0f)
                    .setDuration(200)
                    .setInterpolator(new HyperTanAccelerateInterpolator())
                    .setStartDelay((count - i) * 64)
                    .start();
        }
    }

    public static void glideAwayAndHide(final ViewGroup viewGroup) {
        int count = viewGroup.getChildCount();
        for (int i = 0; i < count; i++) {
            View view = viewGroup.getChildAt(i);
            view.animate()
                    .translationYBy(-MEDIUM_GLIDE_DISTANCE)
                    .alpha(0f)
                    .setDuration(200)
                    .setInterpolator(new HyperTanAccelerateInterpolator())
                    .setStartDelay(i * 16)
                    .start();
        }
    }

    public static void glideInAndShow(final ViewGroup viewGroup) {
        int count = viewGroup.getChildCount();
        for (int i = 0; i < count; i++) {
            View view = viewGroup.getChildAt(i);
            view.animate()
                    .translationYBy(MEDIUM_GLIDE_DISTANCE)
                    .alpha(1f)
                    .setDuration(200)
                    .setInterpolator(new HyperTanDecelerateInterpolator())
                    .setStartDelay((count - i) * 16)
                    .start();
        }
    }

    public static void glideAwayAndHide(final View view) {
        view.animate()
                .translationYBy(-MEDIUM_GLIDE_DISTANCE)
                .alpha(0f)
                .setDuration(200)
                .setInterpolator(new HyperTanAccelerateInterpolator())
                .start();
    }

    public static void glideInAndShow(final View view) {

        view.setAlpha(0f);
        view.setVisibility(View.VISIBLE);
        view.animate()
                .translationYBy(-MEDIUM_GLIDE_DISTANCE)
                .alpha(1f)
                .setDuration(300)
                .setInterpolator(new HyperTanDecelerateInterpolator())
                .start();
    }

    public static void glideOutAndHide(final View view) {
        view.setAlpha(1f);
        view.setTranslationY(-MEDIUM_GLIDE_DISTANCE);
        view.animate()
                .translationYBy(MEDIUM_GLIDE_DISTANCE)
                .alpha(0f)
                .setDuration(300)
                .setInterpolator(new HyperTanAccelerateInterpolator())
                .start();
    }

    public static void circularReveal(View view, int cx, int cy, Animator.AnimatorListener listener) {
        int finalRadius = (int) Math.sqrt(Math.pow(view.getWidth(),2) + Math.pow(view.getHeight(), 2));

        Animator anim = ViewAnimationUtils.createCircularReveal(view, cx, cy, 0, finalRadius);
        anim.setDuration(400);
        anim.setInterpolator(new HyperTanAccelerateInterpolator());
        if (listener != null) {
            anim.addListener(listener);
        }
        view.setVisibility(View.VISIBLE);
        anim.start();
    }

    public static void circularHide(View view, int cx, int cy, Animator.AnimatorListener listener) {
        int finalRadius = (int) Math.sqrt(Math.pow(view.getWidth(),2) + Math.pow(view.getHeight(), 2));

        Animator anim =
                ViewAnimationUtils.createCircularReveal(view, cx, cy, finalRadius, 0);
        anim.setDuration(300);
        anim.setStartDelay(500);
        anim.setInterpolator(new HyperTanDecelerateInterpolator());
        if (listener != null) {
            anim.addListener(listener);
        }
        anim.start();
    }

    public static void animateColor(View view, int colorFrom, int colorTo) {
        final View mView = view;
        final ValueAnimator va = ObjectAnimator.ofArgb(
                colorFrom,
                colorTo);
        va.setDuration(300);
        va.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                         @Override
                         public void onAnimationUpdate(ValueAnimator animation) {
                                     mView.setBackgroundColor((Integer) animation.getAnimatedValue());
                             }
                         }
        );
        va.start();
    }

    public static void animateTextColor(TextView view, int colorFrom, int colorTo) {
        if (view.getCurrentTextColor() == colorFrom) {
            final TextView mView = view;
            final ValueAnimator va = ObjectAnimator.ofArgb(
                    colorFrom,
                    colorTo);
            va.setDuration(300);
            va.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                                     @Override
                                     public void onAnimationUpdate(ValueAnimator animation) {
                                         mView.setTextColor((Integer) animation.getAnimatedValue());
                                     }
                                 }
            );
            va.start();
        }
    }
}
