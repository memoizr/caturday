package com.lovecats.catlover.models.comment;

import com.lovecats.catlover.models.catpost.CatPostEntity;
import com.lovecats.catlover.models.comment.api.CommentApi;

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
