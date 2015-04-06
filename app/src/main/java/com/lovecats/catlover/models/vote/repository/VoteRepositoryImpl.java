package com.lovecats.catlover.models.vote.repository;

import com.lovecats.catlover.models.vote.VoteEntity;
import com.lovecats.catlover.models.vote.datastore.VoteCloudDataStore;

import rx.Observable;

public class VoteRepositoryImpl implements VoteRepository{

    private final VoteCloudDataStore voteCloudDataStore;

    public VoteRepositoryImpl(VoteCloudDataStore voteCloudDataStore) {
        this.voteCloudDataStore = voteCloudDataStore;
    }

    @Override
    public Observable<VoteEntity> sendVote(VoteEntity voteEntity) {


        return voteCloudDataStore.sendVote(voteEntity);
    }
}
