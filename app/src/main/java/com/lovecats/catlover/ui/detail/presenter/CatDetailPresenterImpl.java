package com.lovecats.catlover.ui.detail.presenter;

import android.os.Build;
import android.os.Bundle;
import android.view.MenuItem;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.lovecats.catlover.data.CommentModel;
import com.lovecats.catlover.ui.detail.data.CommentEntity;
import com.lovecats.catlover.ui.detail.interactor.CatDetailInteractor;
import com.lovecats.catlover.ui.detail.view.CatDetailView;
import com.lovecats.catlover.ui.stream.data.CatPostEntity;
import com.lovecats.catlover.util.concurrent.WorkerCallback;

import java.util.ArrayList;
import java.util.List;

public class CatDetailPresenterImpl implements CatDetailPresenter {

    private final CatDetailInteractor catDetailInteractor;
    private final CatDetailView catDetailView;
    private CatPostEntity catPostEntity;

    public CatDetailPresenterImpl(CatDetailView catDetailView,
                                  CatDetailInteractor catDetailInteractor) {

        this.catDetailView = catDetailView;
        this.catDetailInteractor = catDetailInteractor;
    }

    @Override
    public void create(Bundle extras) {
        String url = extras.getString("url");
        String catPostServerId = extras.getString("serverId");

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
        Gson gson = new GsonBuilder().create();
        List<CommentEntity> commentEntities = new ArrayList<>();

        for (JsonElement object : array) {
            CommentEntity commentEntity = gson.fromJson(object, CommentEntity.class);
            commentEntities.add(commentEntity);
        }
        catDetailView.setRecyclerViewAdapter(commentEntities);
    }

    @Override
    public void sendComment(String comment) {
        CommentModel commentModel = new CommentModel();

//        commentModel.setCommentable_id(catPostServerId);
//        commentModel.setCommentable_type(CommentModel.COMMENTABLE_TYPE_CAT_POST);
//        commentModel.setContent(message);
//
//        final User user = UserModel.getLoggedInUser();
//        commentModel.setUser_id(user.getServerId());
//        CommentPoster.postComment(UserModel.getLoggedInUser().getAuthToken(),
//                commentModel, new Callback<CommentModel>() {
//                    @Override
//                    public void success(CommentModel commentModel, Response response) {
//                        Gson gson = new GsonBuilder().create();
//                        String jsonComment = gson.toJson(commentModel);
//                        JsonParser parser = new JsonParser();
//                        JsonObject object = (JsonObject) parser.parse(jsonComment);
//                        adapter.add(object);
//                        adapter.notifyDataSetChanged();
//                    }
//
//                    @Override
//                    public void failure(RetrofitError error) {
//
//                    }
//                });

    }

    @Override
    public void downloadImage() {

    }

    @Override
    public void favoritePost() {

    }

    @Override
    public boolean onMenuItemClick(MenuItem item) {
        return false;
    }
}
