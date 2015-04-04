package com.lovecats.catlover.ui.detail.interactor;

import com.lovecats.catlover.ui.stream.data.CatPostEntity;
import com.lovecats.catlover.util.concurrent.WorkerCallback;

public interface CatDetailInteractor {

    void getPostFromId(String serverId, WorkerCallback<CatPostEntity> callback);
}
