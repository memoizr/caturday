package com.lovecats.catlover.capsules.detail;

import com.lovecats.catlover.AppModule;
import com.lovecats.catlover.models.comment.api.CommentApi;
import com.lovecats.catlover.models.vote.api.VoteApi;
import com.lovecats.catlover.capsules.detail.interactor.CatDetailInteractor;
import com.lovecats.catlover.capsules.detail.interactor.CatDetailInteractorImpl;
import com.lovecats.catlover.capsules.detail.presenter.CatDetailPresenter;
import com.lovecats.catlover.capsules.detail.presenter.CatDetailPresenterImpl;
import com.lovecats.catlover.capsules.detail.view.CatDetailActivity;
import com.lovecats.catlover.capsules.detail.view.CatDetailView;
import com.lovecats.catlover.models.catpost.repository.CatPostRepository;
import com.lovecats.catlover.models.comment.CommentCloudDataStore;
import com.lovecats.catlover.models.comment.repository.CommentRepository;
import com.lovecats.catlover.models.comment.repository.CommentRepositoryImpl;
import com.lovecats.catlover.models.user.UserModule;
import com.lovecats.catlover.models.user.repository.UserRepository;
import com.lovecats.catlover.models.vote.datastore.VoteCloudDataStore;
import com.lovecats.catlover.models.vote.repository.VoteRepository;
import com.lovecats.catlover.models.vote.repository.VoteRepositoryImpl;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import retrofit.RestAdapter;

@Module(
        injects = {
                CatDetailActivity.class
        },
        includes = UserModule.class,
        addsTo = AppModule.class
)
public class CatDetailModule {
    private CatDetailView catDetailView;

    public CatDetailModule(CatDetailView catDetailView) {
        this.catDetailView = catDetailView;
    }

    @Provides
    @Singleton
    public CatDetailView provideCatDetailView() {
        return catDetailView;
    }

    @Provides
    @Singleton
    public CommentApi provideCommentApi(RestAdapter restAdapter) {

        return restAdapter.create(CommentApi.class);
    }

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

    @Provides
    @Singleton
    public CommentCloudDataStore provideCommentCloudDataStore(CommentApi commentApi) {
        return new CommentCloudDataStore(commentApi);
    }

    @Provides
    @Singleton
    public CommentRepository provideCommentRepository(CommentCloudDataStore commentCloudDataStore) {
        return new CommentRepositoryImpl(commentCloudDataStore);
    }

    @Provides
    @Singleton
    public CatDetailInteractor provideCatDetailInteractor(
            CatPostRepository catPostRepository,
            UserRepository userRepository,
            CommentRepository commentRepository,
            VoteRepository voteRepository) {

        return new CatDetailInteractorImpl(catPostRepository,
                userRepository,
                commentRepository,
                voteRepository);
    }

    @Provides
    @Singleton
    public CatDetailPresenter provideCatDetailPresenter(CatDetailView catDetailView,
                                                        CatDetailInteractor catDetailInteractor) {
        return new CatDetailPresenterImpl(catDetailView, catDetailInteractor);
    }
}
