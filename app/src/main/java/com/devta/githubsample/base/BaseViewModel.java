package com.devta.githubsample.base;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.devta.githubsample.data.config.ErrorConfig;
import com.devta.githubsample.data.config.ProgressConfig;

public abstract class BaseViewModel extends ViewModel {

    protected MutableLiveData<ErrorConfig> error = new MutableLiveData<>();
    protected MutableLiveData<ProgressConfig> loader = new MutableLiveData<>();

    public MutableLiveData<ErrorConfig> getError() {
        return error;
    }

    public MutableLiveData<ProgressConfig> getLoader() {
        return loader;
    }
}
