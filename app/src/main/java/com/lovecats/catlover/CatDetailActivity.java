package com.lovecats.catlover;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.annotation.TargetApi;
import android.content.Intent;
import android.graphics.Outline;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.v4.view.ViewCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.transition.Fade;
import android.transition.Transition;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.view.ViewOutlineProvider;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageButton;
import android.widget.ImageView;

import com.lovecats.catlover.data.CatModel;
import com.lovecats.catlover.util.HyperTanDecelerateInterpolator;
import com.squareup.picasso.Picasso;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import greendao.CatImage;
import uk.co.senab.photoview.PhotoViewAttacher;


public class CatDetailActivity extends ActionBarActivity {
    @InjectView(R.id.cat_detail_IV) ImageView cat_detail_IV;
    @InjectView(R.id.favorite_B) ImageButton favorite_B;
    @InjectView(R.id.toolbar) Toolbar toolbar;
    @InjectView(R.id.detail_reveal) View detail_reveal;

    private long id;

    private ActionBarDrawerToggle mDrawerToggle;
    private DrawerLayout mDrawerLayout;

    private String url;
    private Object favorite;

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
        updateButton();
        setUpToolbar();
        scaleButton();
    }

    private void scaleButton() {
        View view = favorite_B;
        view.setScaleX(0f);
        view.setScaleY(0f);
        view.animate()
                .scaleX(1f)
                .scaleY(1f)
                .setDuration(400)
                .setInterpolator(new HyperTanDecelerateInterpolator())
                .setStartDelay(800)
                .start();
    }

    @Override
    protected void onPostResume() {
        super.onPostResume();
        detail_reveal.postDelayed(new Runnable() {
            @Override
            public void run() {
                reveal();
            }
        }, 100);
    }

    private void compatMethods() {
        if (Build.VERSION.SDK_INT >= 21) {
            setUpButton();
            setUpActivityTransitions();
        }
    }

    private void setUpImageView() {
        Picasso.with(this).load(url).into(cat_detail_IV);
        PhotoViewAttacher mAttacher = new PhotoViewAttacher(cat_detail_IV);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    private void setUpButton() {
        ViewOutlineProvider viewOutlineProvider = new ViewOutlineProvider() {
            @Override
            public void getOutline(View view, Outline outline) {
                // Or read size directly from the view's width/height
                int size = getResources().getDimensionPixelSize(R.dimen.fab_size);
                outline.setOval(0, 0, size, size);
            }
        };

        favorite_B.setOutlineProvider(viewOutlineProvider);
        favorite_B.setClipToOutline(true);
    }

    public void reveal() {
        // previously invisible view

        // get the center for the clipping circle
        int cx = (detail_reveal.getLeft() + detail_reveal.getRight()) / 2;
        int cy = (detail_reveal.getTop() + detail_reveal.getBottom()) / 2;
        // get the final radius for the clipping circle
        int finalRadius = (int) Math.sqrt(Math.pow(detail_reveal.getWidth(),2) + Math.pow(detail_reveal.getHeight(), 2) / 2);

        // create the animator for this view (the start radius is zero)
        Animator anim =
                ViewAnimationUtils.createCircularReveal(detail_reveal, cx, cy, 0, finalRadius);
        anim.setDuration(600);
        anim.setInterpolator(new AccelerateInterpolator());

        // make the view visible and start the animation
        detail_reveal.setVisibility(View.VISIBLE);
        anim.start();
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
        toolbar.setOnMenuItemClickListener(
                new Toolbar.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        shareTextUrl();
                        return true;
                    }
                });
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        setDrawerArrow();
    }

    private void getDataFromIntent(){
        Bundle bundle = getIntent().getExtras();
        id = bundle.getLong("id");
        url = bundle.getString("url");
    }

    public void setDrawerArrow() {
        mDrawerLayout = new DrawerLayout(this);
        mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout, toolbar, R.string.action_settings, R.string.action_login);
        mDrawerLayout.post(new Runnable() {
            @Override
            public void run() {
                mDrawerToggle.syncState();
            }
        });
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        mDrawerLayout.setDrawerListener(mDrawerToggle);
    }

    public void toggleArrow(boolean toggle) {
        ValueAnimator va;
        if (toggle) {
            va = new ObjectAnimator().ofFloat(0, 1);
        } else {
            va = new ObjectAnimator().ofFloat(1, 0);
        }
        va.setDuration(200);
        va.setInterpolator(new AccelerateDecelerateInterpolator());
        va.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                mDrawerToggle.onDrawerSlide(mDrawerLayout, Float.parseFloat(animation.getAnimatedValue().toString()));
            }
        });
        va.start();
    }

    private void updateButton() {
        favorite = CatModel.getCatImageForId(this, id).getFavorite();
        Drawable image;
        if (favorite != null && (Boolean) favorite) {
            favorite_B.setBackgroundColor(getResources().getColor(R.color.primary));
            image = getResources().getDrawable(R.drawable.ic_favorite_white_48dp);
            image.mutate().setColorFilter(getResources().getColor(R.color.white), PorterDuff.Mode.MULTIPLY);
        } else {
            favorite_B.setBackgroundColor(getResources().getColor(R.color.white));
            image = getResources().getDrawable(R.drawable.ic_favorite_outline_white_48dp);
            image.mutate().setColorFilter(getResources().getColor(R.color.primary), PorterDuff.Mode.MULTIPLY);
        }
        favorite_B.setImageDrawable(image);
    }

    private void shareTextUrl() {
        Intent share = new Intent(android.content.Intent.ACTION_SEND);
        share.setType("text/plain");
        share.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);

        share.putExtra(Intent.EXTRA_SUBJECT, "Check out this cat!");
        share.putExtra(Intent.EXTRA_TEXT, url);

        startActivity(Intent.createChooser(share, "Share link!"));
    }

    @OnClick(R.id.favorite_B)
    void favoriteImage() {
        CatImage catImage = new CatImage();
        catImage.setId(id);
        catImage.setUrl(url);
        if (favorite != null && (Boolean) favorite) {
            catImage.setFavorite(false);
        } else {
            catImage.setFavorite(true);
        }
        CatModel.insertOrUpdate(this, catImage);
        updateButton();
        Animation animation = AnimationUtils.loadAnimation(this, R.anim.rotate_clockwise);
        favorite_B.startAnimation(animation);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_detail_cat, menu);
        return true;
    }

    @Override
    public void onResume() {
        super.onResume();
        toggleArrow(true);
    }
}
