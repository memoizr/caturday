package com.lovecats.catlover.models.comment.repository;

import com.lovecats.catlover.models.catpost.CatPostEntity;
import com.lovecats.catlover.models.comment.CommentEntity;

import rx.Observable;

public interface CommentRepository {

    Observable<CatPostEntity> sendComment(CommentEntity commentEntity);
}
