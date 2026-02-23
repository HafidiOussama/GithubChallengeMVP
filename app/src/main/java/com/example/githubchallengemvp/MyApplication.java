package com.example.githubchallengemvp;

import android.app.Application;

import com.example.githubchallengemvp.di.AppComponent;
import com.example.githubchallengemvp.di.DaggerAppComponent;

public class MyApplication extends Application {

    private AppComponent appComponent;

    @Override
    public void onCreate() {
        super.onCreate();

        appComponent = DaggerAppComponent.create();
    }

    public AppComponent getAppComponent() {
        return appComponent;
    }
}
