package com.example.githubchallengemvp.di;

import com.example.githubchallengemvp.data.remote.ApiService;
import com.example.githubchallengemvp.data.repository.GithubRepository;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class RepositoryModule {

    @Provides
    @Singleton
    GithubRepository provideGithubRepository(ApiService apiService) {
        return new GithubRepository(apiService);
    }
}
