package com.lovecats.catlover;

import android.graphics.AvoidXfermode;
import android.graphics.Outline;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.support.v4.view.ViewCompat;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.transition.Fade;
import android.transition.Transition;
import android.view.View;
import android.view.ViewOutlineProvider;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;

import com.diegocarloslima.byakugallery.lib.TouchImageView;
import com.lovecats.catlover.data.CatModel;
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
    private long id;

    private String url;
    private Object favorite;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_cat);
        ButterKnife.inject(this);

        Bundle bundle = getIntent().getExtras();
        id = bundle.getLong("id");
        url = bundle.getString("url");


        toolbar.setTitle("");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        Transition fade = new Fade();
        fade.excludeTarget(android.R.id.statusBarBackground, true);
        fade.excludeTarget(android.R.id.navigationBarBackground, true);
        getWindow().setExitTransition(fade);
        getWindow().setEnterTransition(fade);

        ViewCompat.setTransitionName(cat_detail_IV, getIntent().getStringExtra("transition"));
//        cat_detail_IV.setMaxScale(8);

        Picasso.with(this).load(url).into(cat_detail_IV);
        PhotoViewAttacher mAttacher = new PhotoViewAttacher(cat_detail_IV);
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

        updateButton();
    }

    private void updateButton() {
        favorite = CatModel.getCatImageForId(this, id).getFavorite();
        if (favorite != null && (Boolean) favorite) {
            favorite_B.setBackgroundColor(getResources().getColor(R.color.primary));
            Drawable image = getResources().getDrawable(R.drawable.ic_favorite_white_48dp);
            image.mutate().setColorFilter(getResources().getColor(R.color.white), PorterDuff.Mode.MULTIPLY);
            favorite_B.setImageDrawable(image);
        } else {
            favorite_B.setBackgroundColor(getResources().getColor(R.color.white));
            Drawable image = getResources().getDrawable(R.drawable.ic_favorite_outline_white_48dp);
            image.mutate().setColorFilter(getResources().getColor(R.color.primary), PorterDuff.Mode.MULTIPLY);
            favorite_B.setImageDrawable(image);
        }
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
    }

}
