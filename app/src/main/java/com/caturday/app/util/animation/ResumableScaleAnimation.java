package com.caturday.app.util.animation;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.graphics.Matrix;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.ImageView;

import com.caturday.app.util.helper.MathHelper;

public class ResumableScaleAnimation {

    private final float pivotY;
    private final float pivotX;
    private final float toYScale;
    private final float fromYScale;
    private final float fromXScale;
    private final float toXScale;
    private ValueAnimator valueAnimator;
    private int buffer = 0;

    public ResumableScaleAnimation(float fromXScale, float toXScale, float fromYScale, float toYScale, float pivotX, float pivotY) {
        this.fromXScale = fromXScale;
        this.toXScale = toXScale;
        this.fromYScale = fromYScale;
        this.toYScale = toYScale;
        this.pivotX = pivotX;
        this.pivotY = pivotY;
    }

    public void animateImageView(ImageView imageView) {

        imageView.setScaleType(ImageView.ScaleType.MATRIX);

        valueAnimator = new ObjectAnimator().ofFloat(0, 1);
        valueAnimator.setDuration(10000);
        valueAnimator.setInterpolator(new AccelerateDecelerateInterpolator());
        valueAnimator.addUpdateListener(animator -> {
            if (buffer < 7) {
                buffer++;
            } else {
                Matrix m = transformMatrix(animator.getAnimatedFraction());
                imageView.setImageMatrix(m);
                buffer = 0;
            }
        });
        valueAnimator.start();
    }

    private Matrix transformMatrix(float fraction) {
        Matrix m = new Matrix();
        m.reset();

        float xScale = MathHelper.interpolate(fromXScale, toXScale, fraction);
        float yScale = MathHelper.interpolate(fromYScale, toYScale, fraction);

        m.postScale(
                xScale,
                yScale,
                pivotX,
                pivotY
        );

        return  m;
    }

    public void pause() {
        valueAnimator.pause();
    }

    public void resume() {
        valueAnimator.resume();
    }
}
