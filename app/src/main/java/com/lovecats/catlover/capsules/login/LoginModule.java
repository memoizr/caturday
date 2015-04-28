package com.lovecats.catlover.capsules.login;

import com.lovecats.catlover.AppModule;
import com.lovecats.catlover.capsules.login.interactor.LoginInteractor;
import com.lovecats.catlover.capsules.login.interactor.LoginInteractorImpl;
import com.lovecats.catlover.capsules.login.view.LoginActivity;
import com.lovecats.catlover.capsules.login.view.LoginPresenter;
import com.lovecats.catlover.capsules.login.view.LoginPresenterImpl;
import com.lovecats.catlover.capsules.login.view.LoginView;
import com.lovecats.catlover.models.session.SessionModule;
import com.lovecats.catlover.models.session.repository.SessionRepository;
import com.lovecats.catlover.models.user.UserModule;
import com.lovecats.catlover.models.user.repository.UserRepository;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module(
        injects = {
                LoginActivity.class,
        },
        includes = SessionModule.class,
        addsTo = AppModule.class
)
public class LoginModule {

        private final LoginView loginView;

        public LoginModule(LoginView loginView){
                this.loginView = loginView;
        }

        @Provides @Singleton LoginInteractor provideLoginInteractor(SessionRepository sessionRepository) {
               return new LoginInteractorImpl(sessionRepository);
        }

        @Provides @Singleton LoginPresenter provideLoginPresenter(LoginInteractor loginInteractor) {
                return new LoginPresenterImpl(loginView, loginInteractor);
        }
}
