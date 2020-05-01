package com.devta.githubsample.viewmodel;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import com.devta.githubsample.BuildConfig;
import com.devta.githubsample.base.BaseViewModel;
import com.devta.githubsample.data.config.ErrorConfig;
import com.devta.githubsample.data.config.ProgressConfig;
import com.devta.githubsample.data.rest.pojo.GitHubResponse;
import com.devta.githubsample.data.rest.pojo.RepoData;
import com.devta.githubsample.data.rest.repo.GitHubRepository;
import com.devta.headstart.helpers.DevUtil;
import com.devta.headstart.progressdialog.ProgressType;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.observers.DisposableSingleObserver;
import io.reactivex.schedulers.Schedulers;

/**
 * Created on : May, 01, 2020 at 14:15
 * Author     : Divyanshu Tayal
 * Name       : devta-D
 * GitHub     : https://github.com/devta-D/
 * LinkedIn   : https://www.linkedin.com/in/divyanshu-tayal-4a95b2aa/
 */

public class GitHubViewModel extends BaseViewModel {

    private GitHubRepository repository;
    private CompositeDisposable disposable;

    @Inject
    Context context;

    private MutableLiveData<RepoData> data = new MutableLiveData<>();

    @Inject
    public GitHubViewModel(GitHubRepository repository) {
        this.repository = repository;
        this.disposable = new CompositeDisposable();
    }

    public MutableLiveData<RepoData> getData() {
        return data;
    }

    public void search(@NonNull String query, final int page, int per_page) {
        if(!DevUtil.isInternetAvailable(context)) {
            ErrorConfig errorConfig = new ErrorConfig(
                    "Please make sure you have an active internet connection");
            errorConfig.setTitle("Internet Not Found!");
            errorConfig.setShouldFinishActivity(true);
            error.setValue(errorConfig);
            return;
        }
        disposable.add(
                repository.search(query, page, per_page)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableSingleObserver<GitHubResponse>(){
                    @Override
                    public void onSuccess(GitHubResponse response) {
                        loader.setValue(null);
                        if(response != null) {
                            RepoData repoData = new RepoData();
                            repoData.setCurrentPage(page);
                            repoData.setItems(response.getItems());
                            data.setValue(repoData);
                        }else {
                            data.setValue(null);
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        if(BuildConfig.DEBUG) e.printStackTrace();
                        loader.setValue(null);
                        error.setValue(new ErrorConfig(e.getMessage()));
                    }
                })
        );
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        if (disposable != null) {
            disposable.clear();
            disposable = null;
        }
    }
}
