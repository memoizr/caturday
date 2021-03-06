package com.caturday.app.models.comment;

import com.caturday.app.models.catpost.CatPostEntity;
import com.caturday.app.models.comment.api.CommentApi;

import rx.Observable;

public class CommentCloudDataStore {

    private final CommentApi commentApi;

    public CommentCloudDataStore(CommentApi commentApi) {
        this.commentApi = commentApi;
    }

    public Observable<CatPostEntity> sendComment(CommentEntity comment) {
        return commentApi.postComment(comment);
    }
}
