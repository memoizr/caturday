package com.lovecats.catlover.ui.detail.interactor;

import com.lovecats.catlover.ui.detail.data.CommentEntity;
import com.lovecats.catlover.ui.stream.data.CatPostEntity;
import com.lovecats.catlover.util.concurrent.WorkerCallback;

import rx.Observable;

public interface CatDetailInteractor {

    void getPostFromId(String serverId, WorkerCallback<CatPostEntity> callback);

    Observable<CommentEntity> sendComment(String comment, String serverId);
}
