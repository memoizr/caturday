package com.lovecats.catlover.capsules.newpost;

import com.lovecats.catlover.AppModule;
import com.lovecats.catlover.capsules.newpost.interactor.NewPostInteractor;
import com.lovecats.catlover.capsules.newpost.interactor.NewPostInteractorImpl;
import com.lovecats.catlover.capsules.newpost.view.NewPostActivity;
import com.lovecats.catlover.capsules.newpost.view.NewPostPresenter;
import com.lovecats.catlover.capsules.newpost.view.NewPostPresenterImpl;
import com.lovecats.catlover.capsules.newpost.view.NewPostView;
import com.lovecats.catlover.models.catpost.repository.CatPostRepository;
import com.lovecats.catlover.models.user.UserModule;
import com.lovecats.catlover.models.user.repository.UserRepository;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module(
        injects = {
                NewPostActivity.class
        },
        includes = UserModule.class,
        addsTo = AppModule.class
)
public class NewPostModule {

    private NewPostView newPostView;

    public NewPostModule(NewPostView newPostView) {
        this.newPostView = newPostView;
    }

    @Provides
    @Singleton
    public NewPostInteractor provideNewPostInteractor(UserRepository userRepository,
                                                      CatPostRepository catPostRepository) {
        return new NewPostInteractorImpl(userRepository, catPostRepository);
    }

    @Provides @Singleton public NewPostView provideNewPostView() {
        return newPostView;
    }

    @Provides @Singleton public NewPostPresenter provideNewPostPresenter(NewPostView newPostView, NewPostInteractor newPostInteractor) {
        return new NewPostPresenterImpl(newPostView, newPostInteractor);
    }
}
