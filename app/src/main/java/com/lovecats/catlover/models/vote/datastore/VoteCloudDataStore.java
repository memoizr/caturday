package com.lovecats.catlover.models.vote.datastore;

import com.lovecats.catlover.models.vote.api.VoteApi;
import com.lovecats.catlover.models.vote.VoteEntity;

import rx.Observable;

/**
 * Created by Cat#2 on 06/04/15.
 */
public class VoteCloudDataStore {

    private final VoteApi voteApi;

    public VoteCloudDataStore(VoteApi voteApi) {
        this.voteApi = voteApi;
    }

    public Observable<VoteEntity> sendVote(VoteEntity voteEntity) {
        return voteApi.postVote(voteEntity);
    }
}
