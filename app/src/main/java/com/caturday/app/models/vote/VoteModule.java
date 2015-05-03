package com.caturday.app.models.vote;

import com.caturday.app.models.vote.api.VoteApi;
import com.caturday.app.models.vote.datastore.VoteCloudDataStore;
import com.caturday.app.models.vote.repository.VoteRepository;
import com.caturday.app.models.vote.repository.VoteRepositoryImpl;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import retrofit.RestAdapter;

@Module(
        library = true,
        complete = false
)
public class VoteModule {

        @Provides
        @Singleton
        public VoteApi provideVoteApi(RestAdapter adapter) {

                return adapter.create(VoteApi.class);
        }

        @Provides
        @Singleton
        public VoteCloudDataStore provideVoteCloudDataStore(VoteApi voteApi) {
                return new VoteCloudDataStore(voteApi);
        }

        @Provides
        @Singleton
        public VoteRepository provideVoteRepository(VoteCloudDataStore voteCloudDataStore) {
                return new VoteRepositoryImpl(voteCloudDataStore);
        }
}
