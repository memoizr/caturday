package com.lovecats.catlover;

import android.animation.Animator;
import android.annotation.TargetApi;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Outline;
import android.graphics.PorterDuff;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.v4.view.ViewCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.graphics.Palette;
import android.support.v7.widget.Toolbar;
import android.transition.Fade;
import android.transition.Transition;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewOutlineProvider;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.github.ksoichiro.android.observablescrollview.ObservableListView;
import com.github.ksoichiro.android.observablescrollview.ObservableScrollViewCallbacks;
import com.github.ksoichiro.android.observablescrollview.ScrollState;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.lovecats.catlover.adapters.CommentsAdapter;
import com.lovecats.catlover.data.CatPostModel;
import com.lovecats.catlover.helpers.AnimationHelper;
import com.lovecats.catlover.helpers.FullScreenActivitySoftInputHelper;
import com.lovecats.catlover.util.HyperAccelerateDecelerateInterpolator;
import com.lovecats.catlover.util.HyperTanDecelerateInterpolator;
import com.lovecats.catlover.views.ExpandingView;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import greendao.CatImage;
import greendao.CatPost;
import uk.co.senab.photoview.PhotoViewAttacher;


public class CatDetailActivity extends ActionBarActivity {
    @InjectView(R.id.cat_detail_IV) ImageView cat_detail_IV;
    @InjectView(R.id.favorite_B) ImageButton favorite_B;
    @InjectView(R.id.toolbar) Toolbar toolbar;
    @InjectView(R.id.comments_LV) ObservableListView comments_LV;
    @InjectView(R.id.caption_V) ExpandingView caption_V;
    @InjectView(R.id.new_comment_V) View new_comment_V;

    private long id;

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

        compatMethods();
        getDataFromIntent();
        setUpImageView();
        setUpToolbar();

        AnimationHelper.zoomIn(favorite_B);
        setUpListView();
        setUpIMEListener();

        setupPalette((BitmapDrawable) cat_detail_IV.getDrawable());

        caption_V.setBackgroundColor(vibrant);
    }

    private void setupPalette(BitmapDrawable bitmapDrawable) {
        Bitmap mBitmap = bitmapDrawable.getBitmap();
        Palette palette = Palette.generate(mBitmap);
        vibrant = palette.getVibrantColor(0x000000);
        muted = palette.getMutedColor(0x000000);
        darkMuted = palette.getDarkMutedColor(0x000000);
        vibrantLight = palette.getLightVibrantColor(0x000000);
    }

    private void setUpListView() {

        final ArrayList<JsonObject> list = new ArrayList<>();

        JsonParser parser = new JsonParser();
        JsonElement tradeElement = parser.parse(catPost.getComments());
        JsonArray array = tradeElement.getAsJsonArray();
        for (int i = 0; i < array.size(); ++i) {
            list.add(array.get(i).getAsJsonObject());
        }
        final CommentsAdapter adapter = new CommentsAdapter(this, list);

        captionHeight = getResources().getDimensionPixelSize(R.dimen.caption_height);
        FrameLayout content = (FrameLayout) findViewById(android.R.id.content);
        View mChildOfContent = content.getChildAt(0);
        LayoutInflater inflater = getLayoutInflater();

        Rect r = new Rect();
        mChildOfContent.getWindowVisibleDisplayFrame(r);

        header = (ViewGroup)inflater.inflate(R.layout.v_header, comments_LV, false);
        header.getLayoutParams().height = r.bottom;
        comments_LV.addHeaderView(header, null, false);
        comments_LV.setAdapter(adapter);
        comments_LV.setDividerHeight(0);

        comments_LV.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getY() > header.getBottom() - captionHeight) {
                    return false;
                } else {
                    if (!activityClosed) {
                        cat_detail_IV.dispatchTouchEvent(event);
                    }
                    return true;
                }
            }
        });

        final int maxScroll = r.bottom - caption_V.getMaxHeight();
        final int offset = maxScroll - caption_V.getMinHeight();

        comments_LV.setScrollViewCallbacks(new ObservableScrollViewCallbacks() {
            @Override
            public void onScrollChanged(int i, boolean b, boolean b2) {
                new_comment_V.animate().cancel();
                if (i <= maxScroll) {
                    caption_V.setTranslationY((float) -i);
                    if (i >= offset) {
                        favorite_B.setTranslationY((float) - offset);
//                        caption_V.animateBackgroundAccent(vibrant);
                        float fraction = (float) (i - offset)/ caption_V.getMinHeight();
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
                    caption_V.setTranslationY((float) - maxScroll - 2);
                    caption_V.setExpandedLevel(1f);
                }
            }
            @Override
            public void onDownMotionEvent() {  }
            @Override
            public void onUpOrCancelMotionEvent(ScrollState scrollState) {  }
        });

    }

    private void setUpIMEListener() {
        FullScreenActivitySoftInputHelper.assistActivity(this, new FullScreenActivitySoftInputHelper.VisibleSizeChangeListener() {
            @Override
            public void onVisibleSizeChanged(final int heightDifference) {
                if (heightDifference != 0) {
                    new_comment_V.animate()
                            .setInterpolator(new HyperAccelerateDecelerateInterpolator())
                            .setDuration(100)
                            .translationYBy(heightDifference)
                            .setListener(new Animator.AnimatorListener() {
                                @Override
                                public void onAnimationStart(Animator animation) {  }
                                @Override
                                public void onAnimationEnd(Animator animation) {
                                    if (heightDifference < 0) {
                                        comments_LV.setPadding(0, 0, 0, -heightDifference);
                                        comments_LV.smoothScrollBy(-heightDifference, 400);
                                    } else {
                                        comments_LV.smoothScrollBy(-heightDifference, 400);
                                        comments_LV.postDelayed(new Runnable() {
                                            @Override
                                            public void run() {
                                                comments_LV.setPadding(0, 0, 0, 0);
                                            }
                                        }, 416);
                                    }
                                }
                                @Override
                                public void onAnimationCancel(Animator animation) {  }
                                @Override
                                public void onAnimationRepeat(Animator animation) {  }
                            })
                            .start();
                }
            }
        });

    }

    private void compatMethods() {
        if (Build.VERSION.SDK_INT >= 21) {
            setUpActivityTransitions();
        }
    }

    private void setUpImageView() {
        Picasso.with(this).load(url).into(cat_detail_IV);
        PhotoViewAttacher mAttacher = new PhotoViewAttacher(cat_detail_IV);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    private void setUpActivityTransitions(){
        Transition fade = new Fade();
        fade.excludeTarget(android.R.id.statusBarBackground, true);
        fade.excludeTarget(android.R.id.navigationBarBackground, true);
        getWindow().setExitTransition(fade);
        getWindow().setEnterTransition(fade);

        ViewCompat.setTransitionName(cat_detail_IV, getIntent().getStringExtra("transition"));
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

        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                shareTextUrl();
                return false;
            }
        });
    }

    private void getDataFromIntent(){
        Bundle bundle = getIntent().getExtras();
        id = bundle.getLong("id");
        url = bundle.getString("url");
        catPost = CatPostModel.getCatPostForServerId(bundle.getString("serverId"));
    }

    private CatPost catPost;

    private void shareTextUrl() {
        Intent share = new Intent(android.content.Intent.ACTION_SEND);
        share.setType("text/plain");
        share.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);

        share.putExtra(Intent.EXTRA_SUBJECT, "Check out this cat!");
        share.putExtra(Intent.EXTRA_TEXT, url);

        startActivity(Intent.createChooser(share, "Share link!"));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_detail_cat, menu);
        return true;
    }
}
