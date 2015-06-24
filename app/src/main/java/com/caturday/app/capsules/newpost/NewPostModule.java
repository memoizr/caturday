package com.caturday.app.capsules.newpost;

import com.caturday.app.AppModule;
import com.caturday.app.capsules.newpost.interactor.NewPostInteractor;
import com.caturday.app.capsules.newpost.interactor.NewPostInteractorImpl;
import com.caturday.app.capsules.newpost.view.NewPostActivity;
import com.caturday.app.capsules.newpost.view.NewPostPresenter;
import com.caturday.app.capsules.newpost.view.NewPostPresenterImpl;
import com.caturday.app.capsules.newpost.view.NewPostView;
import com.caturday.app.models.catpost.repository.CatPostRepository;
import com.caturday.app.models.user.UserModule;
import com.caturday.app.models.user.repository.UserRepository;
import com.squareup.otto.Bus;

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

    @Provides @Singleton
    public NewPostInteractor provideNewPostInteractor(UserRepository userRepository,
                                                      CatPostRepository catPostRepository) {
        return new NewPostInteractorImpl(userRepository, catPostRepository);
    }

    @Provides @Singleton
    public NewPostView provideNewPostView() {
        return newPostView;
    }

    @Provides @Singleton
    public NewPostPresenter provideNewPostPresenter(
            NewPostView newPostView,
            NewPostInteractor newPostInteractor,
            Bus bus) {
        return new NewPostPresenterImpl(newPostView, newPostInteractor, bus);
    }
}
