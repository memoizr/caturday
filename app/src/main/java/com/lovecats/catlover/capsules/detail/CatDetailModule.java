package com.lovecats.catlover.capsules.detail;

import com.lovecats.catlover.AppModule;
import com.lovecats.catlover.capsules.common.Config;
import com.lovecats.catlover.capsules.detail.api.CommentApi;
import com.lovecats.catlover.capsules.detail.api.VoteApi;
import com.lovecats.catlover.models.comment.CommentCloudDataStore;
import com.lovecats.catlover.models.comment.repository.CommentRepository;
import com.lovecats.catlover.models.comment.repository.CommentRepositoryImpl;
import com.lovecats.catlover.capsules.detail.interactor.CatDetailInteractor;
import com.lovecats.catlover.capsules.detail.interactor.CatDetailInteractorImpl;
import com.lovecats.catlover.capsules.detail.presenter.CatDetailPresenter;
import com.lovecats.catlover.capsules.detail.presenter.CatDetailPresenterImpl;
import com.lovecats.catlover.capsules.detail.view.CatDetailActivity;
import com.lovecats.catlover.capsules.detail.view.CatDetailView;
import com.lovecats.catlover.models.user.db.UserORM;
import com.lovecats.catlover.models.user.repository.UserRepository;
import com.lovecats.catlover.models.user.repository.UserRepositoryImpl;
import com.lovecats.catlover.models.catpost.repository.CatPostRepository;
import com.lovecats.catlover.models.vote.datastore.VoteCloudDataStore;
import com.lovecats.catlover.models.vote.repository.VoteRepository;
import com.lovecats.catlover.models.vote.repository.VoteRepositoryImpl;
import com.lovecats.catlover.util.concurrent.PostExecutionThread;
import com.lovecats.catlover.util.concurrent.ThreadExecutor;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import greendao.DaoSession;
import retrofit.RequestInterceptor;
import retrofit.RestAdapter;


@Module(
        injects = {
                CatDetailActivity.class
        },
        addsTo = AppModule.class
)
public class CatDetailModule {
    private CatDetailView catDetailView;

    public CatDetailModule(CatDetailView catDetailView) {
        this.catDetailView = catDetailView;
    }

    @Provides @Singleton public CatDetailView provideCatDetailView() {
        return catDetailView;
    }

    @Provides @Singleton public UserRepository provideUserRepository(DaoSession daoSession) {
        return new UserRepositoryImpl(new UserORM(daoSession));
    }

    @Provides @Singleton public CommentApi provideCommentApi(UserRepository userRepository) {
        String endpoint = Config.getEndpoint();

        String authToken = userRepository.getCurrentUser().getAuthToken();
        RequestInterceptor interceptor = requestFacade -> requestFacade.addHeader("Auth-Token", authToken);
        RestAdapter adapter = new RestAdapter.Builder()
                .setEndpoint(endpoint)
                .setRequestInterceptor(interceptor)
                .build();

        final CommentApi api = adapter.create(CommentApi.class);
        return api;
    }

    @Provides @Singleton public VoteApi provideVoteApi(UserRepository userRepository) {
        String endpoint = Config.getEndpoint();

        String authToken = userRepository.getCurrentUser().getAuthToken();
        RequestInterceptor interceptor = requestFacade -> requestFacade.addHeader("Auth-Token", authToken);
        RestAdapter adapter = new RestAdapter.Builder()
                .setEndpoint(endpoint)
                .setRequestInterceptor(interceptor)
                .build();

        final VoteApi api = adapter.create(VoteApi.class);
        return api;
    }

    @Provides @Singleton public VoteCloudDataStore provideVoteCloudDataStore(VoteApi voteApi) {
        return new VoteCloudDataStore(voteApi);
    }

    @Provides @Singleton public VoteRepository provideVoteRepository(VoteCloudDataStore voteCloudDataStore) {
        return new VoteRepositoryImpl(voteCloudDataStore);
    }

    @Provides @Singleton public CommentCloudDataStore provideCommentCloudDataStore(CommentApi commentApi) {
        return new CommentCloudDataStore(commentApi);
    }

    @Provides @Singleton public CommentRepository provideCommentRepository(CommentCloudDataStore commentCloudDataStore) {
        return new CommentRepositoryImpl(commentCloudDataStore);
    }

    @Provides @Singleton public CatDetailInteractor provideCatDetailInteractor(
            CatPostRepository catPostRepository,
            UserRepository userRepository,
            CommentRepository commentRepository,
            VoteRepository voteRepository,
            ThreadExecutor threadExecutor,
            PostExecutionThread postExecutionThread) {

        return new CatDetailInteractorImpl(catPostRepository,
                userRepository,
                commentRepository,
                voteRepository,
                threadExecutor,
                postExecutionThread);
    }

    @Provides @Singleton public CatDetailPresenter provideCatDetailPresenter(CatDetailView catDetailView,
                                                        CatDetailInteractor catDetailInteractor) {
        return new CatDetailPresenterImpl(catDetailView, catDetailInteractor);
    }
}
