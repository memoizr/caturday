package com.lovecats.catlover.ui.views;

import android.animation.Animator;
import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.ScaleAnimation;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.lovecats.catlover.R;
import com.lovecats.catlover.ui.stream.data.CatPostEntity;
import com.lovecats.catlover.util.interpolators.HyperTanAccelerateInterpolator;
import com.lovecats.catlover.util.interpolators.HyperTanDecelerateInterpolator;
import com.squareup.picasso.Picasso;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class SlideShowView extends FrameLayout {
    @InjectView(R.id.slide_0) ImageView slide_0;
    @InjectView(R.id.reveal_V) View reveal;

    private Context mContext;

    private AnimationSet zoomIn;
    private ScaleAnimation mScale;

    public SlideShowView(Context context, AttributeSet attrs) {
        super(context, attrs);

        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        inflater.inflate(R.layout.v_slide_show, this, true);
        mContext = context;

        ButterKnife.inject(this);
        cycleBackgroundImage();
    }

    private void setRandomImage() {
//        if (CatPostEntity.getCount() > 0) {
//            String url = CatPostEntity.getRandomCatPost().getImage_url();
//            Picasso.with(mContext).load(url).into(slide_0);
//        }
    }

    private void cycleBackgroundImage() {
        setRandomImage();
        animateBackgroundImage(slide_0);
    }

    private void animateBackgroundImage(ImageView view){
        zoomIn = new AnimationSet(true);
        view.setPivotY(1);
        view.setPivotX(0);
        float rand = (float) (Math.random()/1.3) + 1;
        float randAfter = (float) (Math.random()/1.3) + 1;

        mScale = new ScaleAnimation(
                rand,
                randAfter,
                rand,
                randAfter,
                ScaleAnimation.RELATIVE_TO_PARENT, (float) Math.random(), ScaleAnimation.RELATIVE_TO_PARENT,
                (float) Math.random());
        mScale.setDuration(20000);
        zoomIn.addAnimation(mScale);
        zoomIn.setFillBefore(true);
        zoomIn.setFillAfter(true);
        zoomIn.setFillEnabled(true);

        mScale.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                cycleBackgroundImage();
            }

            @Override
            public void onAnimationRepeat(Animation animation) {}
        });

        view.startAnimation(zoomIn);
    }

    public void animationStart() {
        slide_0.startAnimation(zoomIn);
    }

    public void animationPause() {
        slide_0.clearAnimation();
    }

    public void animationResume() {
    }

    public void flash() {
        reveal();
        fade();
    }


    private void reveal() {
        // previously invisible view

        // get the center for the clipping circle
        int cx = reveal.getWidth() / 2 ;
        int cy = reveal.getTop() + 96;

        // get the final radius for the clipping circle
        int finalRadius = (int) Math.sqrt(Math.pow(reveal.getWidth(),2) + Math.pow(reveal.getHeight(), 2));

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
                .alpha(0f)
                .setStartDelay(400)
                .setDuration(600)
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
