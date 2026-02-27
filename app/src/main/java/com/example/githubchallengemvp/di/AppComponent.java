package com.example.githubchallengemvp.di;

import com.example.githubchallengemvp.MainActivity;

import javax.inject.Singleton;

import dagger.Component;
//AppComponent est le composant principal de Dagger qui relie
// les modules contenant les dépendances et permet leur injection
// automatique dans les classes comme MainActivity.
@Singleton

@Component(modules = {NetworkModule.class, RepositoryModule.class})
public interface AppComponent {
//    Injecte toutes les dépendances nécessaires dans MainActivity?
    void inject(MainActivity mainActivity);
}
