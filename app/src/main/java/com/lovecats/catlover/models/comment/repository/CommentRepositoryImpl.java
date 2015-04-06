package com.lovecats.catlover.models.comment.repository;

import com.lovecats.catlover.models.comment.CommentCloudDataStore;
import com.lovecats.catlover.models.comment.CommentEntity;

import rx.Observable;

/**
 * Created by Cat#2 on 05/04/15.
 */
public class CommentRepositoryImpl implements CommentRepository {

    private final CommentCloudDataStore commentCloudDataStore;

    public CommentRepositoryImpl(CommentCloudDataStore commentCloudDataStore) {
        this.commentCloudDataStore = commentCloudDataStore;
    }


    @Override
    public Observable<CommentEntity> sendComment(CommentEntity commentEntity) {
        return commentCloudDataStore.sendComment(commentEntity);
    }
}

