package com.devta.githubsample.ui.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.devta.githubsample.R;
import com.devta.githubsample.base.BaseActivity;
import com.devta.githubsample.data.rest.pojo.GitHubResponse;
import com.devta.githubsample.data.rest.pojo.RepoData;
import com.devta.githubsample.di.vmutil.ViewModelFactory;
import com.devta.githubsample.util.Constants;
import com.devta.githubsample.viewmodel.GitHubViewModel;
import com.devta.headstart.helpers.DevUtil;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

public class ActivitySplash extends BaseActivity {

    @Inject
    ViewModelFactory viewModelFactory;

    private GitHubViewModel gitHubViewModel;

    @Override
    protected int layoutRes() {
        return R.layout.activity_splash;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(layoutRes());
        gitHubViewModel = ViewModelProviders.of(this, viewModelFactory)
            .get(GitHubViewModel.class);
        setErrorObserver(gitHubViewModel.getError());
        gitHubViewModel.getData().observe(
                this, new Observer<RepoData>() {
            @Override
            public void onChanged(RepoData repoData) {
                goToSearch(repoData.getItems());
            }
        });

        if(DevUtil.isInternetAvailable(getApplicationContext())) {
            gitHubViewModel.search(
                    "android", Constants.DEFAULT_PAGE, Constants.DEFAULT_PER_PAGE);
        }else {
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    goToSearch(null);
                }
            }, 2000L);
        }
    }

    private void goToSearch(ArrayList<GitHubResponse.ItemsBean> items) {
        Intent intent = new Intent(
                ActivitySplash.this, ActivityRepoSearch.class);
        if(items != null && !items.isEmpty())
            intent.putParcelableArrayListExtra(Constants.KEY_DATA, items);
        startActivity(intent);
        ActivityCompat.finishAffinity(ActivitySplash.this);
    }
}
