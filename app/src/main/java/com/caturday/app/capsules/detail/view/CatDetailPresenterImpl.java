package com.caturday.app.capsules.detail.view;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.provider.MediaStore;
import android.view.MenuItem;
import android.widget.Toast;

import com.caturday.app.capsules.common.view.views.ExpandingView;
import com.google.gson.JsonArray;
import com.caturday.app.R;
import com.caturday.app.models.comment.CommentEntity;
import com.caturday.app.capsules.detail.interactor.CatDetailInteractor;
import com.caturday.app.models.catpost.CatPostEntity;
import com.caturday.app.util.helper.ShareHelper;
import com.caturday.app.util.data.GsonConverter;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import java.io.ByteArrayOutputStream;
import java.util.List;

import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class CatDetailPresenterImpl implements CatDetailPresenter {
    
    

    private final CatDetailInteractor catDetailInteractor;
    private final CatDetailView catDetailView;
    private String catPostServerId;
    private Context context;
    private boolean showComment;
    private CatPostEntity catPostEntity;
    private boolean fromNetwork;


    public CatDetailPresenterImpl(Context context,
                                  CatDetailView catDetailView,
                                  CatDetailInteractor catDetailInteractor) {

        this.catDetailView = catDetailView;
        this.catDetailInteractor = catDetailInteractor;
    }

    @Override
    public void create(Bundle extras, Context context) {
        this.context = context;
        catPostServerId = extras.getString(EXTRA_SERVER_ID);
        fromNetwork = extras.getBoolean(EXTRA_FROM_NETWORK);
        showComment = extras.getBoolean(EXTRA_SHOW_COMMENTS);

        getCatPostFromId(catPostServerId);

        if (Build.VERSION.SDK_INT >= 21) {
            catDetailView.initCompat21();
        }

        catDetailView.initRecyclerView();
        catDetailView.initToolbar();
        catDetailView.initIMEListener();

        // TODO automatically log in user.
        if (catDetailInteractor.isUserLoggedIn()) {
            catDetailView.showStuffForLoggedInUser(true);
            catDetailInteractor.isFavorite(catPostServerId)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(catDetailView::updateButton, Throwable::printStackTrace);
        }
    }

    private void getCatPostFromId(String serverId) {
            catDetailInteractor.getPostFromId(serverId, fromNetwork)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(
                            catPostEntity -> {
                                setCatPostEntity(catPostEntity);
                                ExpandingView view = catDetailView.getExpandingView();
                                view.setUsername(catPostEntity.getUser().getUsername());
                                view.setDate(catPostEntity.getCreatedAt());
                                view.setUserImage(catPostEntity.getUser().getImageUrl());
                                catDetailView.initImageView(catPostEntity.getImageUrl());
                                if (showComment) {
                                    catDetailView.showComment();
                                }
                            },
                            Throwable::printStackTrace
                    );
    }

    public void setCatPostEntity(CatPostEntity entity) {
        this.catPostEntity = entity;

        catDetailView.setRecyclerViewAdapter(getCommentEntities(entity));
    }

    private List<CommentEntity> getCommentEntities(CatPostEntity entity) {
        JsonArray array = entity.getComments();

        return GsonConverter.fromJsonArrayToTypeArray(array, CommentEntity.class);
    }

    @Override
    public void sendComment(String comment) {
        if (!catDetailInteractor.isUserLoggedIn()) {
            catDetailView.shakeCommentBox();
            Toast.makeText(context, "Log in to comment on posts", Toast.LENGTH_SHORT).show();
            return;
        }

        if (comment.length() == 0) {
            catDetailView.shakeCommentBox();
            return;
        }
        catDetailView.animateCommentETProcessing();
        catDetailInteractor.sendComment(comment, catPostServerId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        catPostEntity -> {
                            catDetailView.getCommentsAdapter().setCommentEntities(
                                    getCommentEntities(catPostEntity));
                            catDetailView.clearCommentET();
                            catDetailView.scrollToBottom();
                            new Handler().postDelayed(() -> {
                                catDetailView.animateCommentETSuccess();
                            }, 600);
                        },

                        e -> {
                            new Handler().postDelayed(() -> {
                                catDetailView.animateCommentETFailure();
                            }, 600);
                            e.printStackTrace();
                        }
                );
    }

    @Override
    public void favoritePost() {
        if (catDetailInteractor.isUserLoggedIn()) {
            catDetailInteractor
                    .isFavorite(catPostServerId)
                    .subscribeOn(Schedulers.io())
                    .flatMap(positive -> catDetailInteractor.sendVote(catPostServerId, !positive))
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(
                            voteEntity -> {
                                catDetailView.updateButton(voteEntity.getPositive());
                            },

                            Throwable::printStackTrace
                    );
        } else {
            Toast.makeText(context, "Log in to add a post to favorites", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public boolean onMenuItemClick(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.action_share:
                shareTextUrl();
                break;
            // Todo allow users to download images
//            case R.id.action_download:
//                downloadImage();
//                break;
        }
        return true;
    }

    private void shareTextUrl() {
        ShareHelper.shareLinkAction("Check out this cat!", catPostEntity.getImageUrl(), context);
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
            }

            @Override
            public void onPrepareLoad(Drawable arg0) {
            }
        };

        Picasso.with(context)
                .load(catPostEntity.getImageUrl())
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
