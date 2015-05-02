package com.caturday.app.models.comment.repository;

import com.caturday.app.models.catpost.CatPostEntity;
import com.caturday.app.models.comment.CommentEntity;

import rx.Observable;

public interface CommentRepository {

    Observable<CatPostEntity> sendComment(CommentEntity commentEntity);
}
