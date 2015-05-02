package com.caturday.app.capsules.login;

import com.caturday.app.AppModule;
import com.caturday.app.capsules.login.interactor.LoginInteractor;
import com.caturday.app.capsules.login.interactor.LoginInteractorImpl;
import com.caturday.app.capsules.login.view.LoginActivity;
import com.caturday.app.capsules.login.view.LoginPresenter;
import com.caturday.app.capsules.login.view.LoginPresenterImpl;
import com.caturday.app.capsules.login.view.LoginView;
import com.caturday.app.models.session.SessionModule;
import com.caturday.app.models.session.repository.SessionRepository;

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
