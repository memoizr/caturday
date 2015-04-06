package com.lovecats.catlover.models.comment;

import com.lovecats.catlover.capsules.detail.api.CommentApi;

import rx.Observable;

public class CommentCloudDataStore {

    private final CommentApi commentApi;

    public CommentCloudDataStore(CommentApi commentApi) {
        this.commentApi = commentApi;
    }

    public Observable<CommentEntity> sendComment(CommentEntity comment) {
        return commentApi.postComment(comment);
    }
}
