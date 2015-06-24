package com.caturday.app.capsules.detail.view;

import android.animation.Animator;
import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Rect;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.view.ViewCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.Toolbar;
import android.transition.Fade;
import android.transition.Transition;
import android.view.Menu;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.github.ksoichiro.android.observablescrollview.ObservableRecyclerView;
import com.github.ksoichiro.android.observablescrollview.ObservableScrollViewCallbacks;
import com.github.ksoichiro.android.observablescrollview.ScrollState;
import com.caturday.app.R;
import com.caturday.app.util.helper.ColorHelper;
import com.caturday.app.util.helper.FullScreenActivitySoftInputHelper;
import com.caturday.app.capsules.common.view.mvp.BaseAppCompatActivity;
import com.caturday.app.capsules.detail.CatDetailModule;
import com.caturday.app.models.comment.CommentEntity;
import com.caturday.app.capsules.common.view.views.ExpandingView;
import com.caturday.app.util.interpolators.HyperAccelerateDecelerateInterpolator;

import java.util.Collections;
import java.util.List;

import javax.inject.Inject;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import uk.co.senab.photoview.PhotoViewAttacher;

import static com.caturday.app.util.helper.AnimationHelper.*;


public class CatDetailActivity extends BaseAppCompatActivity
        implements CatDetailPresenter.CatDetailView {

    private static final int TAP_DURATION_THRESHOLD_MS = 150;
    @InjectView(R.id.cat_detail_IV) ImageView cat_detail_IV;
    @InjectView(R.id.favorite_B) ImageButton favorite_B;
    @InjectView(R.id.toolbar) Toolbar toolbar;
    @InjectView(R.id.comments_LV) ObservableRecyclerView comments_RV;
    @InjectView(R.id.caption_V) ExpandingView caption_V;
    @InjectView(R.id.new_comment_V) View new_comment_V;
    @InjectView(R.id.comment_TE) EditText comment_ET;
    @InjectView(R.id.comment_controls) View commentControlsV;
    @InjectView(R.id.progress_bar) ProgressBar progressBarPB;

    @Inject CatDetailPresenter catDetailPresenter;

    private int headerBottom;
    private int captionHeight;
    private boolean activityClosed = false;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_cat);

        ButterKnife.inject(this);

        getWindow().getDecorView().setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);

        catDetailPresenter.create(getIntent().getExtras(), this);
    }

    @Override
    protected List<Object> getModules() {
        return Collections.singletonList(new CatDetailModule(this));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_detail_cat, menu);
        return true;
    }

    @OnClick(R.id.favorite_B)
    public void favoritePost() {
        catDetailPresenter.favoritePost();
    }

    @OnClick(R.id.send_message_B)
    public void sendMessage() {
        String message = comment_ET.getText().toString();
        catDetailPresenter.sendComment(message);
    }

    @Override
    public void hideKeyboard() {
        InputMethodManager imm =
                (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
    }

    @Override
    public void initRecyclerView() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        comments_RV.setLayoutManager(layoutManager);
    }

    @Override
    public ExpandingView getExpandingView() {
        return caption_V;
    }

    @Override
    public void initImageView(String imageUrl) {
        Glide.with(this)
                .load(imageUrl)
                .fitCenter()
                .listener(new RequestListener<String, GlideDrawable>() {
                    @Override
                    public boolean onException(
                            Exception e, String model,
                            Target<GlideDrawable> target,
                            boolean isFirstResource) {
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(
                            GlideDrawable resource,
                            String model, Target<GlideDrawable> target,
                            boolean isFromMemoryCache, boolean isFirstResource) {
                        new PhotoViewAttacher(cat_detail_IV);
                        return false;
                    }
                }).into(cat_detail_IV);
    }

    @Override
    public void initIMEListener() {
        FullScreenActivitySoftInputHelper.assistActivity(this,
                heightDifference -> {

                    if (heightDifference != 0) {
                        new_comment_V.animate()
                                .setInterpolator(new HyperAccelerateDecelerateInterpolator())
                                .setDuration(100)
                                .translationYBy(heightDifference)
                                .setListener(new Animator.AnimatorListener() {
                                    @Override
                                    public void onAnimationStart(Animator animation) {
                                    }

                                    @Override
                                    public void onAnimationEnd(Animator animation) {
                                        if (heightDifference < 0) {
                                            comments_RV.setPadding(0, 0, 0, -heightDifference);
                                            comments_RV.smoothScrollBy(0, -heightDifference);
                                        } else {
                                            comments_RV.smoothScrollBy(0, -heightDifference);
                                            comments_RV.postDelayed(() ->
                                                    comments_RV.setPadding(0, 0, 0, 0), 416);
                                        }
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
                });
    }

    @Override
    public void onBackPressed() {
        activityClosed = true;
        super.onBackPressed();
    }

    @Override
    public void initToolbar() {
        toolbar.setTitle("");
        setSupportActionBar(toolbar);

        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_white_larger_24dp);
        toolbar.setNavigationOnClickListener(v -> onBackPressed());

        toolbar.setOnMenuItemClickListener(catDetailPresenter);
    }

    @Override
    public void showComment() {

        int currentScroll = comments_RV.getCurrentScrollY();
        int targetScroll = 2 * caption_V.getHeight();
        if (currentScroll < targetScroll)
        comments_RV.postDelayed(() ->
                comments_RV.smoothScrollBy(0, targetScroll - currentScroll), 200 );
    }

    @Override
    public void setRecyclerViewAdapter(List<CommentEntity> commentEntities) {

        captionHeight = caption_V.getHeight();
        FrameLayout content = (FrameLayout) findViewById(android.R.id.content);
        View mChildOfContent = content.getChildAt(0);

        Rect r = new Rect();
        mChildOfContent.getWindowVisibleDisplayFrame(r);
        final int headerHeight = r.bottom;

        CommentsAdapter adapter =
                new CommentsAdapter(headerHeight);
        adapter.setCommentEntities(commentEntities);
        new_comment_V.setTranslationY(new_comment_V.getHeight());

        comments_RV.setAdapter(adapter);

        comments_RV.setOnTouchListener((v, event) -> {
            if (event.getY() > headerBottom - captionHeight) {
                long duration = android.os.SystemClock.uptimeMillis() - event.getDownTime();
                if (event.getAction() == MotionEvent.ACTION_UP
                        && duration < TAP_DURATION_THRESHOLD_MS) {
                    showComment();
                }
                return false;
            } else {
                if (!activityClosed) {
                    cat_detail_IV.dispatchTouchEvent(event);
                }
                return true;
            }
        });

        final int maxScroll = headerHeight - caption_V.getMaxHeight();
        final int offset = maxScroll - caption_V.getMinHeight();
        int fromColorBG = getResources().getColor(R.color.white_translucent);
        int toColorBG = getResources().getColor(R.color.accent);

        comments_RV.setScrollViewCallbacks(new ObservableScrollViewCallbacks() {
            @Override
            public void onScrollChanged(int i, boolean b, boolean b2) {
                headerBottom = headerHeight - i;
                int newCommentHeight = new_comment_V.getHeight();

                if (i <= maxScroll) {

                    caption_V.setTranslationY((float) -i);

                    if (i >= offset) {
                        float fraction = (float) (i - offset) / caption_V.getMinHeight();

                        favorite_B.setTranslationY((float) -offset);
                        caption_V.setBackgroundColor(
                                ColorHelper.interpolateColor(fromColorBG, toColorBG, fraction));
                        caption_V.setElevation(10 * fraction);
                        caption_V.setExpandedLevel(fraction);
                    } else {
                        favorite_B.setTranslationY((float) -i);
                        caption_V.setBackgroundColor(fromColorBG);
                        caption_V.setElevation(0);
                        caption_V.setExpandedLevel(0);
                    }

                } else {
                    caption_V.setBackgroundColor(toColorBG);
                    caption_V.setTranslationY((float) -maxScroll - 2);
                    caption_V.setExpandedLevel(1f);
                }

                if (i < 2 * newCommentHeight) {
                    new_comment_V.setTranslationY((float) -i / 2 + newCommentHeight);
                }
                // TODO: ensure comment box is fully shown
            }

            @Override
            public void onDownMotionEvent() {
            }

            @Override
            public void onUpOrCancelMotionEvent(ScrollState scrollState) {
            }
        });
    }

    @Override
    public void updateButton(boolean favorited) {
        if (favorited)
            favorite_B.setBackgroundTintList(
                    ColorStateList.valueOf(getResources().getColor(R.color.primary)));
        else
            favorite_B.setBackgroundTintList(
                    ColorStateList.valueOf(getResources().getColor(R.color.teal)));
    }

    @Override
    public void scrollToBottom() {
        int position = comments_RV.getAdapter().getItemCount() -1;
        comments_RV.postDelayed(() -> comments_RV.smoothScrollToPosition(position), 100);
    }

    @Override
    public void initCompat21() {
        setUpActivityTransitions();
    }

    @Override
    public void clearCommentET() {
        comment_ET.setText("");
    }

    @Override
    public CommentsAdapter getCommentsAdapter() {
        return (CommentsAdapter) comments_RV.getAdapter();
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    private void setUpActivityTransitions() {
        Transition fade = new Fade();
        fade.excludeTarget(android.R.id.statusBarBackground, true);
        fade.excludeTarget(android.R.id.navigationBarBackground, true);
        getWindow().setExitTransition(fade);
        getWindow().setEnterTransition(fade);

        ViewCompat.setTransitionName(cat_detail_IV, getIntent().getStringExtra("transition"));
    }

    @Override
    public void animateCommentETProcessing() {
        int height = new_comment_V.getHeight();
        glideUpAndHide(commentControlsV, height);
        progressBarPB.postDelayed(() ->
                glideUpAndShow(progressBarPB, height), 400);
    }

    @Override
    public void animateCommentETSuccess() {
        int height = new_comment_V.getHeight();
        glideUpAndHide(progressBarPB, height);
        progressBarPB.postDelayed(() ->
                glideUpAndShow(commentControlsV, height), 400);
    }

    @Override
    public void animateCommentETFailure() {
        int height = new_comment_V.getHeight();
        glideDownAndHide(progressBarPB, height);
        progressBarPB.postDelayed(() ->
                glideDownAndShow(commentControlsV, height), 400);
        progressBarPB.postDelayed(this::shakeCommentBox, 800);
    }

    @Override
    public void shakeCommentBox() {
        Animation animation = AnimationUtils.loadAnimation(this, R.anim.shake);
        commentControlsV.startAnimation(animation);
    }

    @Override
    public void showStuffForLoggedInUser(boolean isVisible) {
        if (isVisible) {
            favorite_B.setVisibility(View.VISIBLE);
            new_comment_V.setVisibility(View.VISIBLE);
        } else {
            favorite_B.setVisibility(View.GONE);
            new_comment_V.setVisibility(View.GONE);
        }
    }
}
