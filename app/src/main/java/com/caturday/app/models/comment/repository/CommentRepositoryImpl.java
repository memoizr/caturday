package com.caturday.app.models.comment.repository;

import com.caturday.app.models.catpost.CatPostEntity;
import com.caturday.app.models.comment.CommentCloudDataStore;
import com.caturday.app.models.comment.CommentEntity;

import rx.Observable;

public class CommentRepositoryImpl implements CommentRepository {

    private final CommentCloudDataStore commentCloudDataStore;

    public CommentRepositoryImpl(CommentCloudDataStore commentCloudDataStore) {
        this.commentCloudDataStore = commentCloudDataStore;
    }

    @Override
    public Observable<CatPostEntity> sendComment(CommentEntity commentEntity) {
        return commentCloudDataStore.sendComment(commentEntity);
    }
}

