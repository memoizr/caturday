package com.lovecats.catlover.capsules.detail.view;

import android.animation.Animator;
import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.view.ViewCompat;
import android.support.v7.graphics.Palette;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.Toolbar;
import android.transition.Fade;
import android.transition.Transition;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.github.ksoichiro.android.observablescrollview.ObservableRecyclerView;
import com.github.ksoichiro.android.observablescrollview.ObservableScrollViewCallbacks;
import com.github.ksoichiro.android.observablescrollview.ScrollState;
import com.lovecats.catlover.R;
import com.lovecats.catlover.util.helper.FullScreenActivitySoftInputHelper;
import com.lovecats.catlover.capsules.common.BaseActionBarActivity;
import com.lovecats.catlover.capsules.detail.CatDetailModule;
import com.lovecats.catlover.models.comment.CommentEntity;
import com.lovecats.catlover.capsules.detail.presenter.CatDetailPresenter;
import com.lovecats.catlover.capsules.common.view.views.ExpandingView;
import com.lovecats.catlover.util.interpolators.HyperAccelerateDecelerateInterpolator;
import com.squareup.picasso.Picasso;

import java.util.Arrays;
import java.util.List;

import javax.inject.Inject;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import uk.co.senab.photoview.PhotoViewAttacher;


public class CatDetailActivity extends BaseActionBarActivity implements CatDetailView {
    @InjectView(R.id.cat_detail_IV) ImageView cat_detail_IV;
    @InjectView(R.id.favorite_B) ImageButton favorite_B;
    @InjectView(R.id.toolbar) Toolbar toolbar;
    @InjectView(R.id.comments_LV) ObservableRecyclerView comments_RV;
    @InjectView(R.id.caption_V) ExpandingView caption_V;
    @InjectView(R.id.new_comment_V) View new_comment_V;
    @InjectView(R.id.comment_TE) EditText comment_ET;
    @Inject CatDetailPresenter catDetailPresenter;
    int headerBottom;
    private String url;
    private ViewGroup header;
    private int captionHeight;
    private int vibrant;
    private int muted;
    private int darkMuted;
    private int vibrantLight;
    private boolean activityClosed = false;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_cat);

        ButterKnife.inject(this);

        getWindow().getDecorView().setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);

        catDetailPresenter.create(this, getIntent().getExtras());
    }

    @Override
    protected List<Object> getModules() {
        return Arrays.<Object>asList(new CatDetailModule(this));
    }

    private void setupPalette(BitmapDrawable bitmapDrawable) {
        Bitmap mBitmap = bitmapDrawable.getBitmap();
        Palette palette = Palette.generate(mBitmap);
        vibrant = palette.getVibrantColor(0x000000);
        muted = palette.getMutedColor(0x000000);
        darkMuted = palette.getDarkMutedColor(0x000000);
        vibrantLight = palette.getLightVibrantColor(0x000000);
        caption_V.setBackgroundColor(vibrant);
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
    public void initImageView(String imageUrl) {
        Glide.with(this)
                .load(imageUrl)
                .fitCenter()
                .listener(new RequestListener<String, GlideDrawable>() {
                    @Override
                    public boolean onException(Exception e, String model, Target<GlideDrawable> target, boolean isFirstResource) {
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(GlideDrawable resource, String model, Target<GlideDrawable> target, boolean isFromMemoryCache, boolean isFirstResource) {
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
                                            comments_RV.postDelayed(new Runnable() {
                                                @Override
                                                public void run() {
                                                    comments_RV.setPadding(0, 0, 0, 0);
                                                }
                                            }, 416);
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
    public void initToolbar() {
        toolbar.setTitle("");
        setSupportActionBar(toolbar);

        toolbar.setNavigationOnClickListener(v -> onBackPressed());

        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_arrow_back_white_larger_24dp);

        toolbar.setOnMenuItemClickListener(catDetailPresenter);
    }

    @Override
    public void setRecyclerViewAdapter(List<CommentEntity> commentEntities) {

        captionHeight = getResources().getDimensionPixelSize(R.dimen.caption_height);
        FrameLayout content = (FrameLayout) findViewById(android.R.id.content);
        View mChildOfContent = content.getChildAt(0);

        Rect r = new Rect();
        mChildOfContent.getWindowVisibleDisplayFrame(r);
        final int headerHeight = r.bottom;

        CommentsAdapter adapter =
                new CommentsAdapter(headerHeight);
        adapter.setCommentEntities(commentEntities);

        comments_RV.setAdapter(adapter);

        comments_RV.setOnTouchListener((v, event) -> {
            if (event.getY() > headerBottom - captionHeight) {
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

        comments_RV.setScrollViewCallbacks(new ObservableScrollViewCallbacks() {
            @Override
            public void onScrollChanged(int i, boolean b, boolean b2) {
                headerBottom = headerHeight - i;
                new_comment_V.animate().cancel();
                if (i <= maxScroll) {
                    caption_V.setTranslationY((float) -i);
                    if (i >= offset) {
                        favorite_B.setTranslationY((float) -offset);
//                        caption_V.animateBackgroundAccent(vibrant);
                        float fraction = (float) (i - offset) / caption_V.getMinHeight();
                        caption_V.getBackground().setAlpha((int) ((255 - 210) * fraction) + 210);
                        caption_V.setElevation(10 * fraction);
                        caption_V.setExpandedLevel(fraction);
                    } else {
                        favorite_B.setTranslationY((float) -i);
//                        caption_V.animateBackgroundNeutral(vibrant);
                        caption_V.getBackground().setAlpha(210);
                        caption_V.setElevation(0);
                        caption_V.setExpandedLevel(0);
                    }

                    if (i < 400 + new_comment_V.getHeight()) {
                        if (i > 400) {
                            new_comment_V.setTranslationY((float) -i + 400);
                        }
                    } else {
                        new_comment_V.setTranslationY((float) -120);
                    }
                } else {
                    caption_V.getBackground().setAlpha(255);
                    caption_V.setTranslationY((float) -maxScroll - 2);
                    caption_V.setExpandedLevel(1f);
                }
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
    public void initButton() {

    }

    @Override
    public void initCaptionHeader() {

    }

    @Override
    public void initCompat21() {
        setUpActivityTransitions();
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
}
