package com.lovecats.catlover.api;

import com.lovecats.catlover.capsules.common.Config;
import com.lovecats.catlover.models.catpost.api.CatPostApi;
import com.lovecats.catlover.models.user.UserModule;
import com.lovecats.catlover.models.user.repository.UserRepository;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import hugo.weaving.DebugLog;
import retrofit.RequestInterceptor;
import retrofit.RestAdapter;

@Module(
        complete = false,
        includes = UserModule.class,
        library = true
)
public class ApiModule {

        @Provides @Singleton RequestInterceptor provideRequestInterceptor(UserRepository userRepository){

                String tryAuthToken = "";

                if (userRepository.userLoggedIn()) {
                        tryAuthToken = userRepository.getCurrentUser().getAuthToken();
                }

                String authToken = tryAuthToken;

                RequestInterceptor interceptor = requestFacade ->
                        requestFacade.addHeader("Auth-Token", authToken);

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
