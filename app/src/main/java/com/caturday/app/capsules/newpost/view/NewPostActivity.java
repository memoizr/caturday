package com.caturday.app.capsules.newpost.view;

import android.animation.Animator;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.view.ViewGroup;
import android.view.animation.TranslateAnimation;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Spinner;

import com.bumptech.glide.Glide;
import com.caturday.app.R;
import com.caturday.app.capsules.common.view.mvp.BaseActionBarActivity;
import com.caturday.app.capsules.newpost.NewPostModule;
import com.caturday.app.models.catpost.CatPostEntity;
import com.caturday.app.util.helper.AnimationHelper;
import com.caturday.app.util.interpolators.HyperAccelerateDecelerateInterpolator;
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

    private int result = RESULT_CANCELED;

    @Inject NewPostPresenter newPostPresenter;
    @InjectView(R.id.reveal_V) CardView reveal;
    @InjectView(R.id.linear_container) LinearLayout linear_container;
    @InjectView(R.id.toolbar) Toolbar toolbar;
    @InjectView(R.id.preview_IV) ImageView preview;
    @InjectView(R.id.caption_ET) EditText caption;
    @InjectView(R.id.link_ET) EditText link;
    @InjectView(R.id.clear_B) ImageButton clear_B;
    @InjectView(R.id.clear_link_B) ImageButton clear_link_B;
    @InjectView(R.id.category_spinner) Spinner spinner;
    @InjectView(R.id.upload_buttons_LL) ViewGroup uploadButtonsVG;
    @InjectView(R.id.progress_bar) ProgressBar progressBar;
    @InjectView(R.id.done_V) View doneV;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
        setContentView(R.layout.activity_new_post);

        ButterKnife.inject(this);


        if (savedInstanceState == null) {
            newPostPresenter.onCreate(this);
            reveal.postDelayed(() -> reveal(), 200);
        } else
            reveal.setVisibility(View.VISIBLE);

        setUpSpinner();
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

    @Override
    public void onBackPressed() {
        AnimationHelper.glideDownAndHide(linear_container, linear_container.getHeight() / 2);
        new Handler().postDelayed(() -> {
            hide();
        }, 400);
        new Handler().postDelayed(() -> {
            hide();
            super.onBackPressed();
        }, 1000);
    }

    public void reveal() {
        reveal.setVisibility(View.VISIBLE);
        int targetSize1 = reveal.getWidth();
        final int origSize = getResources().getDimensionPixelSize(R.dimen.original_size);
        final int origRadius = origSize/2;
        final int targetRadius1 = getResources().getDimensionPixelSize(R.dimen.target_radius_1);
        final int targetTop = reveal.getTop();

        ValueAnimator revealAnim = ObjectAnimator.ofFloat(1, 0);
        revealAnim.addUpdateListener(animation ->
                transformMaterial(origSize, targetSize1, origRadius, targetRadius1, animation));

        ObjectAnimator translateY = ObjectAnimator.ofFloat(this.reveal, "translationY", (float) -targetTop);
        AnimatorSet aset = new AnimatorSet();
        aset.playTogether(revealAnim, translateY);
        aset.setInterpolator(HyperAccelerateDecelerateInterpolator.getInterpolator());
        aset.setDuration(700);
        aset.start();
    }

    public void hide() {
        int origSize = reveal.getWidth();
        final int targetSize1 = getResources().getDimensionPixelSize(R.dimen.original_size);
        final int targetRadius1 = targetSize1/2;
        final int origRadius = getResources().getDimensionPixelSize(R.dimen.target_radius_1);
        final int targetTop = 0;

        ValueAnimator revealAnim = ObjectAnimator.ofFloat(1, 0);
        revealAnim.addUpdateListener(animation ->
                transformMaterial(origSize, targetSize1, origRadius, targetRadius1, animation));

        ObjectAnimator translateY = ObjectAnimator.ofFloat(this.reveal, "translationY", (float) targetTop);
        AnimatorSet aset = new AnimatorSet();
        aset.playTogether(revealAnim, translateY);
        aset.setInterpolator(HyperAccelerateDecelerateInterpolator.getInterpolator());
        aset.setDuration(700);
        aset.start();
    }

    private void transformMaterial(int origSize,
                                   int targetSize,
                                   int origRadius,
                                   int targetRadius,
                                   ValueAnimator animation) {

        float fraction = (float) animation.getAnimatedValue();
        reveal.setRadius(interpolate(origRadius, targetRadius, fraction));

        reveal.getLayoutParams().width = reveal.getLayoutParams().height
                = (int) ((targetSize - origSize) * (1 - fraction) + origSize);
        reveal.requestLayout();
    }

    private float interpolate(int from, int to, float fraction) {
        return ((from - to) * fraction) + to;
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
        uploadButtonsVG.setVisibility(View.GONE);
    }

    private void hideLinkET() {
        link.setVisibility(View.GONE);
        clear_link_B.setVisibility(View.GONE);
        uploadButtonsVG.setVisibility(View.VISIBLE);
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
        linear_container.postDelayed(() ->
                AnimationHelper.glideUpAndShow(linear_container, linear_container.getHeight()/2)
        , 300);
    }

    @Override
    public void choiceMade() {
        preview.setVisibility(View.VISIBLE);
        clear_B.setVisibility(View.VISIBLE);
        uploadButtonsVG.setVisibility(View.GONE);
    }

    @Override
    public void choiceUnmade() {
        preview.setVisibility(View.GONE);
        clear_B.setVisibility(View.INVISIBLE);
        uploadButtonsVG.setVisibility(View.VISIBLE);
        hideLinkET();
    }

    @Override
    public void setPreview(Uri uri) {

        Glide.with(this).load(uri).into(preview);
    }

    @Override
    public void setPreview(String url) {

        if (url.length() > 0)
            Glide.with(this).load(url).into(preview);
    }

    @Override
    public void onSendPostSuccess(CatPostEntity catPostEntity) {

        result = RESULT_OK;
        Intent intent = new Intent();
        intent.putExtra(NEW_POST_ID, catPostEntity.getServerId());
        setResult(result, intent);
        AnimationHelper.zoomOut(progressBar);
        linear_container.postDelayed(() -> {
            AnimationHelper.zoomInAndShow(doneV);
        }, 300);
        linear_container.postDelayed(() -> {
            onBackPressed();
        }, 600);
    }

    @Override
    public void onSendPostProcessing() {

        AnimationHelper.glideUpAndHide(linear_container, linear_container.getHeight() / 2);
        linear_container.postDelayed(() -> {
            AnimationHelper.glideUpAndShow(progressBar, linear_container.getHeight() / 2);
        }, 700);
    }

    @Override
    public void onSendPostFailure() {
        AnimationHelper.glideDownAndHide(progressBar, linear_container.getHeight() / 2);
        linear_container.postDelayed(() -> {
            AnimationHelper.glideDownAndShow(linear_container, linear_container.getHeight() / 2);
        }, 500);
    }

    @Override
    public void finish() {

        super.finish();
    }

    public void setUpSpinner() {

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.categories_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
    }
}
