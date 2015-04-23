package com.lovecats.catlover.models.comment.repository;

import com.lovecats.catlover.models.catpost.CatPostEntity;
import com.lovecats.catlover.models.comment.CommentCloudDataStore;
import com.lovecats.catlover.models.comment.CommentEntity;

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

