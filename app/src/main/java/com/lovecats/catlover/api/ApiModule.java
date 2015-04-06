package com.lovecats.catlover.api;

import com.lovecats.catlover.common.Config;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import retrofit.RequestInterceptor;

@Module(
        complete = false,
        library = true
)
public class ApiModule {

        @Provides @Singleton
        RequestInterceptor provideRequestInterceptor(){
                String authToken = Config.getAuthToken();
                RequestInterceptor interceptor = new RequestInterceptor() {
                        @Override
                        public void intercept(RequestFacade requestFacade) {
                                requestFacade.addHeader("Auth-Token", authToken);
                        }
                };

                return interceptor;
        }
}
