package com.devta.githubsample.di.module;

import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.devta.githubsample.di.vmutil.ViewModelFactory;
import com.devta.githubsample.di.vmutil.ViewModelKey;
import com.devta.githubsample.viewmodel.GitHubViewModel;

import javax.inject.Singleton;

import dagger.Binds;
import dagger.Module;
import dagger.multibindings.IntoMap;

@Singleton
@Module
public abstract class ViewModelModule {

    @Binds
    @IntoMap
    @ViewModelKey(GitHubViewModel.class)
    abstract ViewModel bindGitHubVM(GitHubViewModel viewModel);

    @Binds
    abstract ViewModelProvider.Factory bindViewModelFactory(ViewModelFactory factory);
}
