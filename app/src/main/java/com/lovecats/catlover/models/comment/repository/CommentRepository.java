package com.lovecats.catlover.models.comment.repository;

import com.lovecats.catlover.models.comment.CommentEntity;

import rx.Observable;

/**
 * Created by Cat#2 on 05/04/15.
 */
public interface CommentRepository {

    Observable<CommentEntity> sendComment(CommentEntity commentEntity);
}
