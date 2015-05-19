package com.caturday.app.util.animation;

import android.view.View;
import android.view.animation.AnimationSet;
import android.widget.ImageView;

import com.daimajia.slider.library.Animations.BaseAnimationInterface;
import com.caturday.app.R;

public class ImageAnimation implements BaseAnimationInterface {

    private AnimationSet zoomIn;
    private ResumableScaleAnimation mScale;
    private ImageView cachedView;

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
        cachedView = view;
        if (cachedView != null) {

            float randBefore;
            float randAfter;

            double random = Math.random();
            float rand = (float) Math.max((random * 0.2) + 1, 1.1);

            if (random < 0.5) {
                randAfter = 1;
                randBefore = rand;
            } else {
                randBefore = 1;
                randAfter = rand;
            }

            mScale = new ResumableScaleAnimation(
                    randBefore,
                    randAfter,
                    randBefore,
                    randAfter,
                    (float) Math.random(),
                    (float) Math.random());

            mScale.animateImageView(cachedView);
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
