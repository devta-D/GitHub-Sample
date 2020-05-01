package com.devta.githubsample.di.module;

import com.devta.githubsample.ui.activity.ActivityRepoSearch;
import com.devta.githubsample.ui.activity.ActivitySplash;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class ActivityBindingModule {

    @ContributesAndroidInjector
    abstract ActivitySplash bindSplashActivity();

    @ContributesAndroidInjector
    abstract ActivityRepoSearch bindRepoSearchActivity();
}
