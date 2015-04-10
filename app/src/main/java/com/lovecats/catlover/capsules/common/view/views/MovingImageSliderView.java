package com.lovecats.catlover.capsules.common.view.views;

import android.animation.Animator;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.widget.ImageView;

import com.daimajia.slider.library.SliderTypes.BaseSliderView;
import com.lovecats.catlover.R;
import com.lovecats.catlover.util.interpolators.HyperTanAccelerateInterpolator;
import com.lovecats.catlover.util.interpolators.HyperTanDecelerateInterpolator;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class MovingImageSliderView extends BaseSliderView {

    @InjectView(R.id.slider_image) ImageView target;
    @InjectView(R.id.reveal_V) View reveal;

    public MovingImageSliderView(Context context) {
        super(context);
    }

    @Override
    public View getView() {

        View v = LayoutInflater.from(getContext()).inflate(R.layout.v_slide_show_page, null);

        ButterKnife.inject(this, v);

        bindEventAndShow(v, target);

        return v;
    }

    public void flash() {
        reveal();
        fade();
    }

    private void reveal() {
        // previously invisible view

        // get the center for the clipping circle
        int cx = reveal.getWidth() / 2;
        int cy = reveal.getTop() + 96;

        // get the final radius for the clipping circle
        int finalRadius = (int) Math.sqrt(Math.pow(reveal.getWidth(), 2) + Math.pow(reveal.getHeight(), 2));

        // create the animator for this view (the start radius is zero)
        Animator anim =
                ViewAnimationUtils.createCircularReveal(reveal, cx, cy, 0, finalRadius);
        anim.setDuration(400);
        anim.setInterpolator(new HyperTanAccelerateInterpolator());

        // make the view visible and start the animation
        reveal.setVisibility(View.VISIBLE);
        anim.start();
    }

    private void fade() {
        reveal.animate()
                .alpha(0f).setStartDelay(400).setDuration(600)
                .setInterpolator(new HyperTanDecelerateInterpolator())
                .setListener(new Animator.AnimatorListener() {
                    @Override
                    public void onAnimationStart(Animator animation) {
                    }

                    @Override
                    public void onAnimationEnd(Animator animation) {
                        reveal.setVisibility(View.INVISIBLE);
                    }

                    @Override
                    public void onAnimationCancel(Animator animation) {
                    }

                    @Override
                    public void onAnimationRepeat(Animator animation) {
                    }
                })
                .start();
    }
}
