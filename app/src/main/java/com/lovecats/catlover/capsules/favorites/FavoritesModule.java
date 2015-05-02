package com.lovecats.catlover.capsules.favorites;

import com.lovecats.catlover.AppModule;
import com.lovecats.catlover.capsules.favorites.interactor.FavoritesInteractor;
import com.lovecats.catlover.capsules.favorites.interactor.FavoritesInteractorImpl;
import com.lovecats.catlover.capsules.favorites.presenter.FavoritesPresenter;
import com.lovecats.catlover.capsules.favorites.presenter.FavoritesPresenterImpl;
import com.lovecats.catlover.capsules.favorites.view.FavoritesFragment;
import com.lovecats.catlover.capsules.favorites.view.FavoritesView;
import com.lovecats.catlover.models.catpost.repository.CatPostRepository;
import com.lovecats.catlover.models.user.UserModule;
import com.lovecats.catlover.models.user.db.UserORM;
import com.lovecats.catlover.models.user.repository.UserRepository;
import com.lovecats.catlover.models.user.repository.UserRepositoryImpl;
import com.squareup.otto.Bus;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import greendao.DaoSession;

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
