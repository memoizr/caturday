package com.lovecats.catlover.ui.detail;

import com.lovecats.catlover.AppModule;
import com.lovecats.catlover.common.Config;
import com.lovecats.catlover.ui.detail.api.CommentApi;
import com.lovecats.catlover.ui.detail.data.CommentCloudDataStore;
import com.lovecats.catlover.ui.detail.data.repository.CommentRepository;
import com.lovecats.catlover.ui.detail.data.repository.CommentRepositoryImpl;
import com.lovecats.catlover.ui.detail.interactor.CatDetailInteractor;
import com.lovecats.catlover.ui.detail.interactor.CatDetailInteractorImpl;
import com.lovecats.catlover.ui.detail.presenter.CatDetailPresenter;
import com.lovecats.catlover.ui.detail.presenter.CatDetailPresenterImpl;
import com.lovecats.catlover.ui.detail.view.CatDetailActivity;
import com.lovecats.catlover.ui.detail.view.CatDetailView;
import com.lovecats.catlover.ui.profile.data.repository.UserRepository;
import com.lovecats.catlover.ui.profile.data.repository.UserRepositoryImpl;
import com.lovecats.catlover.ui.stream.data.repository.CatPostRepository;
import com.lovecats.catlover.util.concurrent.PostExecutionThread;
import com.lovecats.catlover.util.concurrent.ThreadExecutor;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
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

    @Provides @Singleton public UserRepository provideUserRepository() {
        return new UserRepositoryImpl();
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
            ThreadExecutor threadExecutor,
            PostExecutionThread postExecutionThread) {

        return new CatDetailInteractorImpl(catPostRepository,
                userRepository,
                commentRepository,
                threadExecutor,
                postExecutionThread);
    }

    @Provides @Singleton public CatDetailPresenter provideCatDetailPresenter(CatDetailView catDetailView,
                                                        CatDetailInteractor catDetailInteractor) {
        return new CatDetailPresenterImpl(catDetailView, catDetailInteractor);
    }
}
