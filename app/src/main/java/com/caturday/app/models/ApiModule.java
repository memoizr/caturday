package com.caturday.app.models;

import com.caturday.app.capsules.common.Config;
import com.caturday.app.models.catpost.api.CatPostApi;
import com.caturday.app.models.session.SessionModule;
import com.caturday.app.models.session.repository.SessionRepository;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import retrofit.RequestInterceptor;
import retrofit.RestAdapter;

@Module(
        complete = false,
        includes = SessionModule.class,
        library = true
)
public class ApiModule {

    @Provides RequestInterceptor provideRequestInterceptor(
            SessionRepository sessionRepository){

        String tryAuthToken = sessionRepository.currentSession().getAuthToken();

        RequestInterceptor interceptor = requestFacade ->
                requestFacade.addHeader("Auth-Token", tryAuthToken);

        return interceptor;
    }

    @Provides RestAdapter provideRestAdapter(RequestInterceptor requestInterceptor) {

        String endpoint = Config.getEndpoint();

        RestAdapter adapter = new RestAdapter.Builder()
                .setEndpoint(endpoint)
                .setLogLevel(RestAdapter.LogLevel.NONE)
                .setRequestInterceptor(requestInterceptor)
                .build();
        return adapter;
    }

    @Provides @Singleton
    public CatPostApi provideCatPostApi(RestAdapter restAdapter) {

        final CatPostApi api = restAdapter.create(CatPostApi.class);
        return api;
    }
}
