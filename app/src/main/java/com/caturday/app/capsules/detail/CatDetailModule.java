package com.caturday.app.capsules.detail;

import android.content.Context;

import com.caturday.app.AppModule;
import com.caturday.app.models.comment.api.CommentApi;
import com.caturday.app.models.vote.api.VoteApi;
import com.caturday.app.capsules.detail.interactor.CatDetailInteractor;
import com.caturday.app.capsules.detail.interactor.CatDetailInteractorImpl;
import com.caturday.app.capsules.detail.view.CatDetailPresenter;
import com.caturday.app.capsules.detail.view.CatDetailPresenterImpl;
import com.caturday.app.capsules.detail.view.CatDetailActivity;
import com.caturday.app.models.catpost.repository.CatPostRepository;
import com.caturday.app.models.comment.CommentCloudDataStore;
import com.caturday.app.models.comment.repository.CommentRepository;
import com.caturday.app.models.comment.repository.CommentRepositoryImpl;
import com.caturday.app.models.user.UserModule;
import com.caturday.app.models.user.repository.UserRepository;
import com.caturday.app.models.vote.datastore.VoteCloudDataStore;
import com.caturday.app.models.vote.repository.VoteRepository;
import com.caturday.app.models.vote.repository.VoteRepositoryImpl;

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
    private CatDetailPresenter.CatDetailView catDetailView;

    public CatDetailModule(CatDetailPresenter.CatDetailView catDetailView) {
        this.catDetailView = catDetailView;
    }

    @Provides
    @Singleton
    public CatDetailPresenter.CatDetailView provideCatDetailView() {
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
    public CatDetailPresenter provideCatDetailPresenter(Context context, CatDetailPresenter.CatDetailView catDetailView,
                                                        CatDetailInteractor catDetailInteractor) {
        return new CatDetailPresenterImpl(context, catDetailView, catDetailInteractor);
    }
}
