package com.lovecats.catlover.views;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.ScaleAnimation;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.lovecats.catlover.R;
import com.lovecats.catlover.data.CatModel;
import com.squareup.picasso.Picasso;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class SlideShowView extends FrameLayout {
    @InjectView(R.id.slide_0) ImageView slide_0;

    private Context mContext;

    public SlideShowView(Context context, AttributeSet attrs) {
        super(context, attrs);

        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        inflater.inflate(R.layout.v_slide_show, this, true);
        mContext = context;

        ButterKnife.inject(this);
        String url = CatModel.getCatImageForId(mContext, (long) Math.ceil(40 * Math.random())).getUrl();
        Picasso.with(mContext).load(url).into(slide_0);
        cycleBackgroundImage();
    }

    private void cycleBackgroundImage() {
        String url = CatModel.getCatImageForId(mContext, (long) Math.ceil(40 * Math.random())).getUrl();
        Picasso.with(mContext).load(url).into(slide_0);
        animateBackgroundImage(slide_0);
    }

    AnimationSet zoomIn;
    ScaleAnimation mScale;

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
}
