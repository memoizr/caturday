package com.lovecats.catlover.models;

import com.lovecats.catlover.capsules.common.Config;
import com.lovecats.catlover.models.catpost.api.CatPostApi;
import com.lovecats.catlover.models.session.SessionModule;
import com.lovecats.catlover.models.session.repository.SessionRepository;
import com.lovecats.catlover.models.user.repository.UserRepository;

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

        @Provides @Singleton RequestInterceptor provideRequestInterceptor(
                SessionRepository sessionRepository){

                String tryAuthToken = sessionRepository.currentSession().getAuthToken();

                RequestInterceptor interceptor = requestFacade ->
                        requestFacade.addHeader("Auth-Token", tryAuthToken);

                return interceptor;
        }

        @Provides @Singleton RestAdapter provideRestAdapter(RequestInterceptor requestInterceptor) {

                String endpoint = Config.getEndpoint();

                RestAdapter adapter = new RestAdapter.Builder()
                        .setEndpoint(endpoint)
                        .setRequestInterceptor(requestInterceptor)
                        .build();
                return adapter;
        }

        @Provides
        @Singleton
        public CatPostApi provideCatPostApi(RestAdapter restAdapter) {

                final CatPostApi api = restAdapter.create(CatPostApi.class);
                return api;
        }
}
