package com.example.githubchallengemvp.di;

import com.example.githubchallengemvp.MainActivity;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = {NetworkModule.class, RepositoryModule.class})
public interface AppComponent {

    void inject(MainActivity mainActivity);
}
