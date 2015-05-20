package com.caturday.app.capsules.newpost.view;

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
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Spinner;

import com.bumptech.glide.Glide;
import com.caturday.app.R;
import com.caturday.app.capsules.common.view.mvp.BaseAppCompatActivity;
import com.caturday.app.capsules.newpost.NewPostModule;
import com.caturday.app.models.catpost.CatPostEntity;
import com.caturday.app.util.helper.AnimationHelper;
import com.caturday.app.util.helper.MathHelper;
import com.caturday.app.util.interpolators.HyperAccelerateDecelerateInterpolator;

import java.util.Arrays;
import java.util.List;

import javax.inject.Inject;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;


public class NewPostActivity extends BaseAppCompatActivity implements NewPostView {
    public static final String NEW_POST_ID = "NEW_POST_ID";
    public static final int NEW_POST_REQUEST_CODE = 2;
    public static final String EXTRA_ORIGIN_LEFT = "ORIGIN_LEFT";
    public static final String EXTRA_ORIGIN_TOP = "ORIGIN_TOP";
    public static final String EXTRA_ORIGIN_HEIGHT = "ORIGIN_HEIGHT";
    public static final String EXTRA_ORIGIN_WIDTH = "ORIGIN_WIDTH";
    public static final String EXTRA_ORIGIN_RADIUS = "ORIGIN_RADIUS";

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
    @InjectView(R.id.submit_B) View submitB;
    @InjectView(R.id.image_container_V) View imageContainerV;
    private int result = RESULT_CANCELED;
    private int containerHeight;
    private boolean isClosing;
    private int originTop;
    private int originLeft;
    private int origWidth;
    private int origHeight;
    private int origRadius;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
        setContentView(R.layout.activity_new_post);

        ButterKnife.inject(this);

        originLeft = getIntent().getExtras().getInt(EXTRA_ORIGIN_LEFT);
        originTop = getIntent().getExtras().getInt(EXTRA_ORIGIN_TOP);
        origWidth = getIntent().getExtras().getInt(EXTRA_ORIGIN_WIDTH);
        origHeight = getIntent().getExtras().getInt(EXTRA_ORIGIN_HEIGHT);
        origRadius = getIntent().getExtras().getInt(EXTRA_ORIGIN_RADIUS);


        if (savedInstanceState == null) {
            newPostPresenter.onCreate(this);

            animateIn();
        } else
            reveal.setVisibility(View.VISIBLE);

        containerHeight = linear_container.getHeight();

        setUpSpinner();
    }

    @Override
    protected List<Object> getModules() {
        return Arrays.asList(new NewPostModule(this));
    }

    public void reveal() {

        assistReveal();
        int targetX = reveal.getWidth();
        int targetY = reveal.getHeight();
        final int targetRadius = getResources().getDimensionPixelSize(R.dimen.target_radius_1);

        FrameLayout.MarginLayoutParams layoutParams =
                (FrameLayout.MarginLayoutParams) reveal.getLayoutParams();

        final int targetTop = originTop - getStatusBarHeight() - layoutParams.topMargin;

        final int targetLeft = originLeft - layoutParams.leftMargin;

        ValueAnimator revealAnim = ObjectAnimator.ofFloat(0, 1);
        revealAnim.addUpdateListener(animation ->
                transformMaterial(origWidth, origHeight, targetX, targetY, origRadius, targetRadius, (float) animation.getAnimatedFraction()));

        reveal.setTranslationX(targetLeft);
        reveal.setTranslationY(targetTop);
        ObjectAnimator translateX = ObjectAnimator.ofFloat(this.reveal, "translationX", (float) 0);
        ObjectAnimator translateY = ObjectAnimator.ofFloat(this.reveal, "translationY", (float) 0);
        AnimatorSet aset = new AnimatorSet();
        aset.playTogether(revealAnim, translateY, translateX);
        aset.setInterpolator(HyperAccelerateDecelerateInterpolator.getInterpolator());
        aset.setDuration(700);
        aset.start();
    }

    public int getStatusBarHeight() {
      int result = 0;
      int resourceId = getResources().getIdentifier("status_bar_height", "dimen", "android");
      if (resourceId > 0) {
          result = getResources().getDimensionPixelSize(resourceId);
      }
      return result;
}

    public void setUpSpinner() {

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.categories_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
    }

    private void assistReveal() {

        reveal.setVisibility(View.VISIBLE);
        View navIcon = toolbar.getChildAt(0);
        navIcon.setRotation(45);
        navIcon.setScaleX(1.2f);
        navIcon.setScaleY(1.2f);

        submitB.setAlpha(0f);
        submitB.animate()
                .alpha(1f)
                .setStartDelay(200)
                .setDuration(700)
                .start();

        navIcon.animate()
                .setDuration(1000)
                .rotation(90)
                .scaleY(1f)
                .scaleX(1f)
                .setInterpolator(HyperAccelerateDecelerateInterpolator.getInterpolator())
                .start();
    }

    private void transformMaterial(int origX,
                                   int origY,
                                   int targetX,
                                   int targetY,
                                   int origRadius,
                                   int targetRadius,
                                   float fraction) {


        reveal.setRadius(MathHelper.interpolate(origRadius, targetRadius, fraction));

        reveal.getLayoutParams().width =
                (int) MathHelper.interpolate(origX, targetX, fraction);

        reveal.getLayoutParams().height =
                (int) MathHelper.interpolate(origY, targetY, fraction);

        reveal.requestLayout();
    }

    @Override
    public void initToolbar() {
        toolbar.setTitle("");
        setSupportActionBar(toolbar);

        toolbar.setNavigationIcon(R.drawable.ic_close_white_24dp);
        toolbar.setNavigationOnClickListener(v -> onBackPressed());
    }

    public void hide() {

        assistHide();
        int origX = reveal.getWidth();
        int origY = reveal.getHeight();

        FrameLayout.MarginLayoutParams layoutParams =
                (FrameLayout.MarginLayoutParams) reveal.getLayoutParams();

        final int origRadius = getResources().getDimensionPixelSize(R.dimen.target_radius_1);
        final int targetTop = originTop - getStatusBarHeight() - layoutParams.topMargin;
        final int targetLeft = originLeft - layoutParams.leftMargin;

        ValueAnimator revealAnim = ObjectAnimator.ofFloat(0, 1);

        revealAnim.addUpdateListener(animation ->
                transformMaterial(origX, origY, origWidth, origHeight, origRadius, this.origRadius, (float) animation.getAnimatedValue()));

        ObjectAnimator translateX = ObjectAnimator.ofFloat(this.reveal, "translationX", (float) targetLeft);
        ObjectAnimator translateY = ObjectAnimator.ofFloat(this.reveal, "translationY", (float) targetTop);

        AnimatorSet aset = new AnimatorSet();
        aset.playTogether(revealAnim, translateX, translateY);
        aset.setInterpolator(HyperAccelerateDecelerateInterpolator.getInterpolator());
        aset.setDuration(500);
        aset.start();
    }

    private void assistHide() {
        View navIcon = toolbar.getChildAt(0);

        submitB.animate()
                .alpha(0f)
                .setDuration(300)
                .start();

        navIcon.animate()
                .setDuration(500)
                .rotation(45)
                .scaleY(1.2f)
                .scaleX(1.2f)
                .setInterpolator(HyperAccelerateDecelerateInterpolator.getInterpolator())
                .start();
    }

    @Override
    public void animateIn() {

        int minSize = getResources().getDimensionPixelSize(R.dimen.size_xxxlarge);

        int delay = (origHeight < minSize) ? 600 : 300;
        reveal.postDelayed(() -> reveal(), 200);

        linear_container.postDelayed(() -> {
            linear_container.setVisibility(View.VISIBLE);
            AnimationHelper.glideUpAndShow(linear_container, linear_container.getHeight());
        }, delay);
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

        submitB.setEnabled(false);
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
            submitB.setEnabled(true);
        }, 500);
    }

    @Override
    public void shakeOptionButtons() {
        Animation animation = AnimationUtils.loadAnimation(this, R.anim.shake);
        imageContainerV.startAnimation(animation);
    }

    @OnClick(R.id.upload_image_B)
    public void uploadImage() {
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

    @OnClick(R.id.clear_link_B)
    public void clearET() {
        if (link.getText().toString().length() == 0)
            choiceUnmade();
        else
            link.setText("");
    }

    @OnClick(R.id.take_photo_B)
    public void takePhoto() {
        newPostPresenter.takeNewImage();
    }

    @OnClick(R.id.submit_B)
    public void submit() {
        newPostPresenter.sendPost(caption, link, spinner);
    }

    @OnClick(R.id.clear_B)
    public void clear() {
        choiceUnmade();
    }

    private void hideLinkET() {
        link.setVisibility(View.GONE);
        clear_link_B.setVisibility(View.GONE);
        uploadButtonsVG.setVisibility(View.VISIBLE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        newPostPresenter.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onBackPressed() {
        if (!isClosing) {
            isClosing = true;
            AnimationHelper.glideDownAndHide(linear_container, linear_container.getHeight() / 2);
            new Handler().postDelayed(() -> {
                hide();
            }, 400);
            new Handler().postDelayed(() -> {
                super.onBackPressed();
            }, 1000);
        }
    }

    @Override
    public void finish() {

        super.finish();
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
    }
}
