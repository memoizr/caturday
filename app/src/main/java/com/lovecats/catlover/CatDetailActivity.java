package com.lovecats.catlover;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.graphics.Outline;
import android.os.Build;
import android.os.PersistableBundle;
import android.os.SystemClock;
import android.support.v4.view.ViewCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.app.ActionBarDrawerToggle;
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
import android.view.accessibility.AccessibilityEvent;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;

import com.lovecats.catlover.util.HyperTanDecelerateInterpolator;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

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
    @InjectView(R.id.comments_LV)  ListView comments_LV;
    @InjectView(R.id.detail_container_RL) RelativeLayout detail_container;

    private long id;

    private ActionBarDrawerToggle mDrawerToggle;
    private DrawerLayout mDrawerLayout;

    private String url;
    private Object favorite;
    private ViewGroup header;

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

        String[] values = new String[] { "Android", "iPhone", "WindowsMobile",
                "Blackberry", "WebOS", "Ubuntu", "Windows7", "Max OS X",
                "Linux", "OS/2", "Ubuntu", "Windows7", "Max OS X", "Linux",
                "OS/2", "Ubuntu", "Windows7", "Max OS X", "Linux", "OS/2",
                "Android", "iPhone", "WindowsMobile" };

        final ArrayList<String> list = new ArrayList<String>();
        for (int i = 0; i < values.length; ++i) {
            list.add(values[i]);
        }
        final StableArrayAdapter adapter = new StableArrayAdapter(this,
                android.R.layout.simple_list_item_1, list);
        LayoutInflater inflater = getLayoutInflater();
        header = (ViewGroup)inflater.inflate(R.layout.v_header, comments_LV, false);
        header.getLayoutParams().height = 1000;
        comments_LV.addHeaderView(header, null, false);
        comments_LV.setAdapter(adapter);

        comments_LV.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getY() > header.getBottom()) {
                    return false;
                } else {
                    cat_detail_IV.dispatchTouchEvent(event);
                    return true;
                }
            }
        });

    }

    private class StableArrayAdapter extends ArrayAdapter<String> {

        HashMap<String, Integer> mIdMap = new HashMap<String, Integer>();

        public StableArrayAdapter(Context context, int textViewResourceId,
                                  List<String> objects) {
            super(context, textViewResourceId, objects);
            for (int i = 0; i < objects.size(); ++i) {
                mIdMap.put(objects.get(i), i);
            }
        }

        @Override
        public long getItemId(int position) {
            String item = getItem(position);
            return mIdMap.get(item);
        }

        @Override
        public boolean hasStableIds() {
            return true;
        }

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
                .setStartDelay(400)
                .start();
    }
//
//    @Override
//    protected void onPostResume() {
//        super.onPostResume();
//        detail_reveal.postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                reveal();
//            }
//        }, 100);
//    }

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

//    public void reveal() {
//        // previously invisible view
//
//        // get the center for the clipping circle
//        int cx = (detail_reveal.getLeft() + detail_reveal.getRight()) / 2;
//        int cy = (detail_reveal.getTop() + detail_reveal.getBottom()) / 2;
//        // get the final radius for the clipping circle
//        int finalRadius = (int) Math.sqrt(Math.pow(detail_reveal.getWidth(),2) + Math.pow(detail_reveal.getHeight(), 2) / 2);
//
//        // create the animator for this view (the start radius is zero)
//        Animator anim =
//                ViewAnimationUtils.createCircularReveal(detail_reveal, cx, cy, 0, finalRadius);
//        anim.setDuration(600);
//        anim.setInterpolator(new AccelerateInterpolator());
//
//        // make the view visible and start the animation
//        detail_reveal.setVisibility(View.VISIBLE);
//        anim.start();
//    }

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
    }

    private void getDataFromIntent(){
        Bundle bundle = getIntent().getExtras();
        id = bundle.getLong("id");
        url = bundle.getString("url");
    }

    private void updateButton() {
//        favorite = CatModel.getCatImageForId(this, id).getFavorite();
//        Drawable image;
//        if (favorite != null && (Boolean) favorite) {
//            favorite_B.setBackgroundColor(getResources().getColor(R.color.primary));
//            image = getResources().getDrawable(R.drawable.ic_favorite_white_48dp);
//            image.mutate().setColorFilter(getResources().getColor(R.color.white), PorterDuff.Mode.MULTIPLY);
//        } else {
//            favorite_B.setBackgroundColor(getResources().getColor(R.color.white));
//            image = getResources().getDrawable(R.drawable.ic_favorite_outline_white_48dp);
//            image.mutate().setColorFilter(getResources().getColor(R.color.primary), PorterDuff.Mode.MULTIPLY);
//        }
//        favorite_B.setImageDrawable(image);
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
//        CatModel.insertOrUpdate(this, catImage);
        updateButton();
        Animation animation = AnimationUtils.loadAnimation(this, R.anim.rotate_clockwise);
        favorite_B.startAnimation(animation);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_detail_cat, menu);
        return true;
    }

}
