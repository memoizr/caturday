package com.lovecats.catlover.capsules.newpost.view;

import android.animation.Animator;

import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.widget.LinearLayout;

import com.lovecats.catlover.R;
import com.lovecats.catlover.capsules.common.BaseActionBarActivity;
import com.lovecats.catlover.capsules.newpost.NewPostModule;
import com.lovecats.catlover.util.interpolators.HyperTanAccelerateInterpolator;
import com.lovecats.catlover.util.interpolators.HyperTanDecelerateInterpolator;

import java.util.Arrays;
import java.util.List;

import javax.inject.Inject;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;


public class NewPostActivity extends BaseActionBarActivity implements NewPostView {
    @Inject NewPostPresenter newPostPresenter;
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

        newPostPresenter.onCreate(this);
    }

    @Override
    protected List<Object> getModules() {
        return Arrays.asList(new NewPostModule(this));
    }

    @Override
    public void initToolbar(){
        toolbar.setTitle("");
        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(v -> onBackPressed());
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_arrow_back_white_larger_24dp);
    }

    public void reveal() {
        int cx = reveal.getRight() - 48;
        int cy = reveal.getBottom() - 96;
        int finalRadius = (int) Math.sqrt(Math.pow(reveal.getWidth(),2) + Math.pow(reveal.getHeight(), 2));
        Animator anim =
                ViewAnimationUtils.createCircularReveal(reveal, cx, cy, 0, finalRadius);
        anim.setDuration(400);
        anim.setInterpolator(new HyperTanAccelerateInterpolator());
        reveal.setVisibility(View.VISIBLE);
        anim.start();
    }

    @Override
    protected void onPostResume() {
        super.onPostResume();
        reveal.postDelayed(() -> reveal(), 32);
    }

    @OnClick(R.id.upload_image_B)
    public void uploadImage(){
        newPostPresenter.chooseImage();
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        newPostPresenter.onActivityResult(data);
    }

    @Override
    public void animateIn() {
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
}
