package com.caturday.app.util.helper;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.view.ViewGroup;
import android.widget.TextView;

import com.caturday.app.util.interpolators.HyperTanAccelerateInterpolator;
import com.caturday.app.util.interpolators.HyperTanDecelerateInterpolator;

public class AnimationHelper {
    private static final int GLIDE_DISTANCE = 400;
    private static final int MEDIUM_GLIDE_DISTANCE = 200;

    @Deprecated
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
                    .setInterpolator(HyperTanDecelerateInterpolator.getInterpolator())
                    .setStartDelay(i * 64 + 600)
                    .start();
        }
    }


    public static void zoomOut(View view) {
        view.animate()
                .scaleX(0f)
                .scaleY(0f)
                .setDuration(300)
                .setInterpolator(HyperTanAccelerateInterpolator.getInterpolator())
                .start();
    }

    public static void zoomInAndShow(View view) {
        view.setVisibility(View.VISIBLE);
        view.setScaleY(0f);
        view.setScaleX(0f);
        view.animate()
                .scaleX(1f)
                .scaleY(1f)
                .setDuration(300)
                .setInterpolator(HyperTanDecelerateInterpolator.getInterpolator())
                .start();
    }

    @Deprecated
    public static void glideDown(ViewGroup viewGroup) {
        int count = viewGroup.getChildCount();
        for (int i = 0; i < count; i++) {
            View view = viewGroup.getChildAt(i);
            view.animate()
                    .translationYBy(GLIDE_DISTANCE)
                    .alpha(0f)
                    .setDuration(200)
                    .setInterpolator(HyperTanAccelerateInterpolator.getInterpolator())
                    .setStartDelay((count - i) * 64)
                    .start();
        }
    }

    @Deprecated
    public static void glideAwayAndHide(final ViewGroup viewGroup) {
        int count = viewGroup.getChildCount();
        for (int i = 0; i < count; i++) {
            View view = viewGroup.getChildAt(i);
            view.animate()
                    .translationYBy(-MEDIUM_GLIDE_DISTANCE)
                    .alpha(0f)
                    .setDuration(200)
                    .setInterpolator(HyperTanAccelerateInterpolator.getInterpolator())
                    .setStartDelay(i * 16)
                    .start();
        }
    }

    @Deprecated
    public static void glideInAndShowOld(final ViewGroup viewGroup) {
        int count = viewGroup.getChildCount();
        for (int i = 0; i < count; i++) {
            View view = viewGroup.getChildAt(i);
            view.animate()
                    .translationYBy(MEDIUM_GLIDE_DISTANCE)
                    .alpha(1f)
                    .setDuration(200)
                    .setInterpolator(HyperTanDecelerateInterpolator.getInterpolator())
                    .setStartDelay((count - i) * 16)
                    .start();
        }
    }

    @Deprecated
    public static void glideAwayAndHide(final View view) {
        view.animate()
                .translationYBy(-MEDIUM_GLIDE_DISTANCE)
                .alpha(0f)
                .setDuration(200)
                .setInterpolator(HyperTanAccelerateInterpolator.getInterpolator())
                .start();
    }

    @Deprecated
    public static void glideInAndShowOld(final View view) {

        view.setAlpha(0f);
        view.setVisibility(View.VISIBLE);
        view.animate()
                .translationYBy(-MEDIUM_GLIDE_DISTANCE)
                .alpha(1f)
                .setDuration(300)
                .setInterpolator(HyperTanDecelerateInterpolator.getInterpolator())
                .start();
    }

    /**
     * Make a visible ViewGroup disappear, with an upwards animation.
     *
     * @param viewGroup
     * @param glideDistance
     */
    public static void glideUpAndHide(ViewGroup viewGroup, int glideDistance) {
        int count = viewGroup.getChildCount();
        for (int i = 0; i < count; i++) {
            View view = viewGroup.getChildAt(i);
            view.animate()
                    .translationYBy(-glideDistance)
                    .alpha(0f)
                    .setDuration(300)
                    .setInterpolator(HyperTanAccelerateInterpolator.getInterpolator())
                    .setStartDelay(i * 64)
                    .start();
        }
    }

    /**
     * Make a visible ViewGroup disappear, with an upwards animation.
     *
     * @param viewGroup
     * @param glideDistance
     */
    public static void glideDownAndHide(ViewGroup viewGroup, int glideDistance) {
        int count = viewGroup.getChildCount();
        for (int i = 0; i < count; i++) {
            View view = viewGroup.getChildAt(i);
            view.animate()
                    .translationYBy(glideDistance)
                    .alpha(0f)
                    .setDuration(300)
                    .setInterpolator(HyperTanAccelerateInterpolator.getInterpolator())
                    .setStartDelay((count - i) * 64)
                    .start();
        }
    }

    /**
     * Make an hidden ViewGroup appear from the bottom, with an upwards animation.
     *
     * @param viewGroup
     * @param glideDistance
     */
    public static void glideDownAndShow(ViewGroup viewGroup, int glideDistance) {
        int count = viewGroup.getChildCount();
        for (int i = 0; i < count; i++) {
            View view = viewGroup.getChildAt(i);
            view.setTranslationY(-glideDistance);
            view.setAlpha(0f);
            view.animate()
                    .translationYBy(glideDistance)
                    .alpha(1f)
                    .setDuration(300)
                    .setInterpolator(HyperTanDecelerateInterpolator.getInterpolator())
                    .setStartDelay((count - i) * 64)
                    .start();
        }
    }

    /**
     * Make an hidden ViewGroup appear from the bottom, with an upwards animation.
     *
     * @param viewGroup
     * @param glideDistance
     */
    public static void glideUpAndShow(ViewGroup viewGroup, int glideDistance) {
        int count = viewGroup.getChildCount();
        for (int i = 0; i < count; i++) {
            View view = viewGroup.getChildAt(i);
            view.setTranslationY(glideDistance);
            view.setAlpha(0f);
            view.animate()
                    .translationYBy(-glideDistance)
                    .alpha(1f)
                    .setDuration(300)
                    .setInterpolator(HyperTanDecelerateInterpolator.getInterpolator())
                    .setStartDelay(i * 64)
                    .start();
        }
    }

    /**
     * Make a view disappear, with an upwards animation.
     *
     * @param view
     * @param glideDistance
     */
    public static void glideUpAndHide(View view, int glideDistance) {
        view.animate()
                .translationYBy(-glideDistance)
                .alpha(0f)
                .setDuration(300)
                .setInterpolator(HyperTanAccelerateInterpolator.getInterpolator())
                .start();
    }

    /**
     * Make an hidden view appear from the bottom, with an upwards animation.
     *
     * @param view
     * @param glideDistance
     */
    public static void glideUpAndShow(final View view, int glideDistance) {

        view.setAlpha(0f);
        view.setVisibility(View.VISIBLE);
        view.setTranslationY(glideDistance);
        view.animate()
                .translationYBy(-glideDistance)
                .alpha(1f)
                .setDuration(400)
                .setInterpolator(HyperTanDecelerateInterpolator.getInterpolator())
                .start();
    }

    /**
     * Make a view disappear, with a downwards animation.
     *
     * @param view
     * @param glideDistance
     */
    public static void glideDownAndHide(View view, int glideDistance) {
        view.animate()
                .translationYBy(glideDistance)
                .alpha(0f)
                .setDuration(300)
                .setInterpolator(HyperTanAccelerateInterpolator.getInterpolator())
                .start();
    }

    /**
     * Make an hidden view appear from the bottom, with a downwards animation.
     *
     * @param view
     * @param glideDistance
     */
    public static void glideDownAndShow(final View view, int glideDistance) {

        view.setAlpha(0f);
        view.setVisibility(View.VISIBLE);
        view.setTranslationY(glideDistance);
        view.animate()
                .translationYBy(-glideDistance)
                .alpha(1f)
                .setDuration(400)
                .setInterpolator(HyperTanDecelerateInterpolator.getInterpolator())
                .start();
    }

    public static void glideOutAndHide(final View view) {
        view.setAlpha(1f);
        view.setTranslationY(-MEDIUM_GLIDE_DISTANCE);
        view.animate()
                .translationYBy(MEDIUM_GLIDE_DISTANCE)
                .alpha(0f)
                .setDuration(300)
                .setInterpolator(HyperTanAccelerateInterpolator.getInterpolator())
                .start();
    }

    public static void circularReveal(View view, int cx, int cy, Animator.AnimatorListener listener) {
        int finalRadius = (int) Math.sqrt(Math.pow(view.getWidth(),2) + Math.pow(view.getHeight(), 2));

        Animator anim = ViewAnimationUtils.createCircularReveal(view, cx, cy, 0, finalRadius);
        anim.setDuration(400);
        anim.setInterpolator(HyperTanAccelerateInterpolator.getInterpolator());
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
        anim.setInterpolator(HyperTanDecelerateInterpolator.getInterpolator());
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
        va.addUpdateListener(animation ->
                        mView.setBackgroundColor((Integer) animation.getAnimatedValue())
        );
        va.start();
    }
}
