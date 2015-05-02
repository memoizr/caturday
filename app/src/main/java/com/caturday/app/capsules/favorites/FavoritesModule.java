package com.caturday.app.capsules.favorites;

import com.caturday.app.AppModule;
import com.caturday.app.capsules.favorites.interactor.FavoritesInteractor;
import com.caturday.app.capsules.favorites.interactor.FavoritesInteractorImpl;
import com.caturday.app.capsules.favorites.presenter.FavoritesPresenter;
import com.caturday.app.capsules.favorites.presenter.FavoritesPresenterImpl;
import com.caturday.app.capsules.favorites.view.FavoritesFragment;
import com.caturday.app.capsules.favorites.view.FavoritesView;
import com.caturday.app.models.catpost.repository.CatPostRepository;
import com.caturday.app.models.user.UserModule;
import com.caturday.app.models.user.repository.UserRepository;
import com.squareup.otto.Bus;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module(
        injects = {
                FavoritesFragment.class
        },
        includes = UserModule.class,
        addsTo = AppModule.class
)
public class FavoritesModule {

    private final FavoritesView favoritesView;

    public FavoritesModule(FavoritesView favoritesView) {
        this.favoritesView = favoritesView;
    }


//    @Provides @Singleton public UserRepository provideUserRepository(DaoSession daoSession) {
//        return new UserRepositoryImpl(new UserORM(daoSession));
//    }

    @Provides @Singleton FavoritesInteractor provideFavoriteInteractor(
            UserRepository userRepository,
            CatPostRepository catPostRepository) {
        return new FavoritesInteractorImpl(userRepository, catPostRepository);
    }

    @Provides @Singleton FavoritesPresenter provideFavoritesPresenter(
            FavoritesInteractor favoritesInteractor,
            Bus bus) {

        return new FavoritesPresenterImpl(favoritesView, favoritesInteractor, bus);
    }

}
