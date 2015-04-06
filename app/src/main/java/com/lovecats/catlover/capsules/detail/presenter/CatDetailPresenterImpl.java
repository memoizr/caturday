package com.lovecats.catlover.capsules.detail.presenter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.MenuItem;

import com.google.gson.JsonArray;
import com.lovecats.catlover.R;
import com.lovecats.catlover.models.comment.CommentEntity;
import com.lovecats.catlover.capsules.detail.interactor.CatDetailInteractor;
import com.lovecats.catlover.capsules.detail.view.CatDetailView;
import com.lovecats.catlover.models.catpost.CatPostEntity;
import com.lovecats.catlover.util.data.GsonConverter;
import com.lovecats.catlover.util.concurrent.WorkerCallback;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import java.io.ByteArrayOutputStream;
import java.util.List;

import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class CatDetailPresenterImpl implements CatDetailPresenter {

    private final CatDetailInteractor catDetailInteractor;
    private final CatDetailView catDetailView;
    private CatPostEntity catPostEntity;
    private String url;
    private String catPostServerId;
    private Context context;

    public CatDetailPresenterImpl(CatDetailView catDetailView,
                                  CatDetailInteractor catDetailInteractor) {

        this.catDetailView = catDetailView;
        this.catDetailInteractor = catDetailInteractor;
    }

    @Override
    public void create(Context context, Bundle extras) {
        this.context = context;
        url = extras.getString("url");
        catPostServerId = extras.getString("serverId");

        getCatPostFromId(catPostServerId);

        if (Build.VERSION.SDK_INT >= 21) {
            catDetailView.initCompat21();
        }

        catDetailView.initRecyclerView();
        catDetailView.initImageView(url);
        catDetailView.initToolbar();
        catDetailView.initIMEListener();
    }

    private void getCatPostFromId(String serverId) {
        catDetailInteractor.getPostFromId(serverId, new WorkerCallback<CatPostEntity>() {
            public void done(CatPostEntity entity) {
                setCatPostEntity(entity);
            }
        });
    }

    public void setCatPostEntity(CatPostEntity entity) {
        catPostEntity = entity;
        JsonArray array = catPostEntity.getComments();

        List<CommentEntity> commentEntities =
                GsonConverter.fromJsonArrayToTypeArray(array, CommentEntity.class);

        catDetailView.setRecyclerViewAdapter(commentEntities);
    }

    @Override
    public void sendComment(String comment) {

        catDetailInteractor.sendComment(comment, catPostServerId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(commentEntity -> {
                    catDetailView.getCommentsAdapter().addCommentEntity(commentEntity);
                });
    }

    @Override
    public void favoritePost() {
    }

    @Override
    public boolean onMenuItemClick(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.action_share:
                System.out.println("share");
                shareTextUrl();
                break;
            case R.id.action_download:
                System.out.println("download");
                downloadImage();
                break;
        }
        return true;
    }

    private void shareTextUrl() {
        Intent share = new Intent(android.content.Intent.ACTION_SEND);

        share.setType("text/plain");
        share.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        share.putExtra(Intent.EXTRA_SUBJECT, "Check out this cat!");
        share.putExtra(Intent.EXTRA_TEXT, url);

        context.startActivity(Intent.createChooser(share, "Share link!"));
    }

    private void downloadImage() {
        final String fileName = catPostServerId + ".jpg";
        Target target = new Target() {

            @Override
            public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom arg1) {

                try {

                    ByteArrayOutputStream bytes = new ByteArrayOutputStream();
                    bitmap.compress(Bitmap.CompressFormat.JPEG, 100, bytes);

                    String path = MediaStore.Images.Media.insertImage(context.getContentResolver(),
                            bitmap, fileName, null);

                    Uri uri = Uri.parse(path);

                    setImageAs(uri);

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onBitmapFailed(Drawable arg0) {
                return;
            }

            @Override
            public void onPrepareLoad(Drawable arg0) {
                return;
            }
        };

        Picasso.with(context)
                .load(url)
                .into(target);
    }

    private void setImageAs(Uri uri) {
        Intent intent = new Intent(Intent.ACTION_ATTACH_DATA);
        intent.addCategory((Intent.CATEGORY_DEFAULT));
        intent.setDataAndType(uri, "image/jpeg");
        intent.putExtra("mimeType", "image/jpg");

        context.startActivity(Intent.createChooser(intent, "Set as:"));
    }
}
