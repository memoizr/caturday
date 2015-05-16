package com.caturday.app.capsules.newpost.view;

import android.animation.Animator;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.bumptech.glide.Glide;
import com.caturday.app.R;
import com.caturday.app.capsules.common.view.mvp.BaseActionBarActivity;
import com.caturday.app.capsules.newpost.NewPostModule;
import com.caturday.app.models.catpost.CatPostEntity;
import com.caturday.app.util.interpolators.HyperTanAccelerateInterpolator;
import com.caturday.app.util.interpolators.HyperTanDecelerateInterpolator;
import com.squareup.picasso.Picasso;

import java.util.Arrays;
import java.util.List;

import javax.inject.Inject;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;


public class NewPostActivity extends BaseActionBarActivity implements NewPostView {
    public static final String NEW_POST_ID = "NEW_POST_ID";
    public static final int NEW_POST_REQUEST_CODE = 2;
    @Inject NewPostPresenter newPostPresenter;
    @InjectView(R.id.reveal_V) View reveal;
    @InjectView(R.id.linear_container) LinearLayout linear_container;
    @InjectView(R.id.toolbar) Toolbar toolbar;
    @InjectView(R.id.choices_HSV) View choices;
    @InjectView(R.id.preview_IV) ImageView preview;
    @InjectView(R.id.caption_ET) EditText caption;
    @InjectView(R.id.link_ET) EditText link;
    @InjectView(R.id.clear_B) ImageButton clear_B;
    @InjectView(R.id.clear_link_B) ImageButton clear_link_B;
    private int result = RESULT_CANCELED;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_post);

        ButterKnife.inject(this);

        getWindow().getDecorView().setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);


        if (savedInstanceState == null) {
            newPostPresenter.onCreate(this);
            reveal.postDelayed(() -> reveal(), 32);
        } else
            reveal.setVisibility(View.VISIBLE);
    }

    @Override
    protected List<Object> getModules() {
        return Arrays.asList(new NewPostModule(this));
    }

    @Override
    public void initToolbar(){
        toolbar.setTitle("");
        setSupportActionBar(toolbar);

        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_white_larger_24dp);
        toolbar.setNavigationOnClickListener(v -> onBackPressed());
    }

    public void reveal() {
        int cx = reveal.getRight() - 96;
        int cy = reveal.getBottom() - 96;
        int finalRadius = (int) Math.sqrt(Math.pow(reveal.getWidth(),2) + Math.pow(reveal.getHeight(), 2));
        Animator anim =
                ViewAnimationUtils.createCircularReveal(reveal, cx, cy, 0, finalRadius);
        anim.setDuration(400);
        anim.setInterpolator(new HyperTanAccelerateInterpolator());
        reveal.setVisibility(View.VISIBLE);
        anim.start();
    }

    @OnClick(R.id.upload_image_B)
    public void uploadImage(){
        newPostPresenter.chooseImage();
    }

    @OnClick(R.id.link_image_B)
    public void attachLink() {
        showLinkET();
        link.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                setPreview(s.toString());
                choiceMade();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    private void showLinkET() {
        link.setVisibility(View.VISIBLE);
        clear_link_B.setVisibility(View.VISIBLE);
        choices.setVisibility(View.GONE);
    }

    private void hideLinkET() {
        link.setVisibility(View.GONE);
        clear_link_B.setVisibility(View.GONE);
        choices.setVisibility(View.VISIBLE);
    }

    @OnClick(R.id.clear_link_B)
    public void clearET() {
        link.setText("");
    }

    @OnClick(R.id.take_photo_B)
    public void takePhoto() {
        newPostPresenter.takeNewImage();
    }

    @OnClick(R.id.submit_B)
    public void submit() {
        newPostPresenter.sendPost(caption, link);
    }

    @OnClick(R.id.clear_B)
    public void clear() {
        choiceUnmade();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        newPostPresenter.onActivityResult(data);
    }

    @Override
    public void animateIn() {
        int count = linear_container.getChildCount();
        for (int i = 0; i < count; i++) {
            View view = linear_container.getChildAt(i);
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


    @Override
    public void choiceMade() {
        preview.setVisibility(View.VISIBLE);
        clear_B.setVisibility(View.VISIBLE);
        choices.setVisibility(View.GONE);
    }

    @Override
    public void choiceUnmade() {
        preview.setVisibility(View.GONE);
        clear_B.setVisibility(View.INVISIBLE);
        choices.setVisibility(View.VISIBLE);
        hideLinkET();
    }

    @Override
    public void setPreview(Uri uri) {
        Glide.with(this).load(uri).into(preview);
    }

    @Override
    public void setPreview(String url) {
        if (url.length() > 0)
            Picasso.with(this).load(url).fit().centerInside().into(preview);
    }

    @Override
    public void success(CatPostEntity catPostEntity) {
        result = RESULT_OK;
        Intent intent = new Intent();
        intent.putExtra(NEW_POST_ID, catPostEntity.getServerId());
        setResult(result, intent);
        onBackPressed();
    }

    @Override
    public void finish() {
        super.finish();
    }
}
