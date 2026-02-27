package com.example.githubchallengemvp.di;

import com.example.githubchallengemvp.data.remote.ApiService;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

@Module
public class NetworkModule {
//    l’adresse principale de l’API GitHub.
    private static final String BASE_URL = "https://api.github.com/";
//    NetworkModule est un module Dagger responsable de la configuration réseau
    @Provides
    @Singleton
        //Singleton garantit qu’une seule instance
        // du Repository existe pendant tout le cycle de vie de l’application.
    Retrofit provideRetrofit() {
        return new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    @Provides
    @Singleton
    ApiService provideApiService(Retrofit retrofit) {
        return retrofit.create(ApiService.class);
    }
}
