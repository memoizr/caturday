package com.lovecats.catlover.ui.detail.data.repository;

import com.lovecats.catlover.ui.detail.data.CommentEntity;

import rx.Observable;

/**
 * Created by Cat#2 on 05/04/15.
 */
public interface CommentRepository {

    Observable<CommentEntity> sendComment(CommentEntity commentEntity);
}
