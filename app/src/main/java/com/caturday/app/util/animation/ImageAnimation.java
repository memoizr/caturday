package com.caturday.app.util.animation;

import android.view.View;
import android.view.animation.AnimationSet;
import android.view.animation.ScaleAnimation;
import android.widget.ImageView;

import com.daimajia.slider.library.Animations.BaseAnimationInterface;
import com.caturday.app.R;

public class ImageAnimation implements BaseAnimationInterface {

    private AnimationSet zoomIn;
    private ResumableScaleAnimation mScale;

    @Override
    public void onPrepareCurrentItemLeaveScreen(View view) {
    }

    @Override
    public void onPrepareNextItemShowInScreen(View view) {
        animateBackgroundImage((ImageView) view.findViewById(R.id.slider_image));
    }

    @Override
    public void onCurrentItemDisappear(View view) {
    }

    @Override
    public void onNextItemAppear(View view) {
    }

    private void animateBackgroundImage(ImageView view) {
        if (view != null) {
            zoomIn = new AnimationSet(true);
            float rand = (float) (Math.random() / 1.3) + 1;
            float randAfter = (float) (Math.random() / 1.3) + 1;

            mScale = new ResumableScaleAnimation(
                    rand,
                    randAfter,
                    rand,
                    randAfter,
                    ScaleAnimation.RELATIVE_TO_PARENT,
                    (float) Math.random(),
                    ScaleAnimation.RELATIVE_TO_PARENT,
                    (float) Math.random());
            mScale.setDuration(10000);
            zoomIn.addAnimation(mScale);
            zoomIn.setFillBefore(true);
            zoomIn.setFillAfter(true);
            view.startAnimation(zoomIn);
        }
    }

    public void pauseAnimation() {
        if (mScale != null) {
            mScale.pause();
        }
    }

    public void resumeAnimation() {
        if (mScale != null) {
            mScale.resume();
        }
    }
}
