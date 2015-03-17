package com.lovecats.catlover;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.TimeInterpolator;
import android.animation.ValueAnimator;
import android.support.v7.app.ActionBarActivity;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.transition.Explode;
import android.transition.Transition;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.widget.Button;
import android.widget.LinearLayout;

import com.lovecats.catlover.util.HyperTanAccelerateInterpolator;
import com.lovecats.catlover.util.HyperTanDecelerateInterpolator;

import butterknife.ButterKnife;
import butterknife.InjectView;


public class NewPostActivity extends ActionBarActivity {
    @InjectView(R.id.reveal_V) LinearLayout reveal;
    @InjectView(R.id.new_post_title) View new_post_title;
    @InjectView(R.id.toolbar) Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_post);

        ButterKnife.inject(this);

        getWindow().getDecorView().setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
        setUpToolbar();

        int count = reveal.getChildCount();
        for (int i = 0; i < count; i++) {
            View view = reveal.getChildAt(i);
            view.setTranslationY(400);
            view.setAlpha(0f);
            view.animate()
                    .translationYBy(-400)
                    .alpha(1f)
                    .setDuration(400)
                    .setInterpolator(new HyperTanDecelerateInterpolator())
                    .setStartDelay(i * 100 + 600)
                    .start();
        }
    }

    private void setUpToolbar(){
        toolbar.setTitle("");
        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_arrow_back_white_larger_24dp);
    }

    public void reveal() {
        // previously invisible view

        // get the center for the clipping circle
        int cx = reveal.getRight() - 48;
        int cy = reveal.getBottom() - 96;

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

    @Override
    protected void onPostResume() {
        super.onPostResume();
        reveal.postDelayed(new Runnable() {
            @Override
            public void run() {
                reveal();
            }
        }, 32);
    }
}
