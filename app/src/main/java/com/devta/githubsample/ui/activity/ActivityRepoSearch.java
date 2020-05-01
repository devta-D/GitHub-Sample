package com.devta.githubsample.ui.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.text.method.KeyListener;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.AbsListView;

import com.devta.githubsample.R;
import com.devta.githubsample.base.BaseActivity;
import com.devta.githubsample.data.config.ProgressConfig;
import com.devta.githubsample.data.rest.pojo.GitHubResponse;
import com.devta.githubsample.data.rest.pojo.RepoData;
import com.devta.githubsample.databinding.ActivityRepoSearchBinding;
import com.devta.githubsample.di.vmutil.ViewModelFactory;
import com.devta.githubsample.ui.adapter.RepoAdapter;
import com.devta.githubsample.util.Constants;
import com.devta.githubsample.viewmodel.GitHubViewModel;
import com.devta.headstart.helpers.DevUtil;
import com.devta.headstart.progressdialog.ProgressType;
import com.malinskiy.superrecyclerview.OnMoreListener;

import java.security.Key;
import java.util.ArrayList;

import javax.inject.Inject;

public class ActivityRepoSearch extends BaseActivity
    implements TextWatcher, View.OnKeyListener, OnMoreListener {

    private ActivityRepoSearchBinding binding;
    private RepoAdapter repoAdapter;
    private String query;

    private int currentPage = 1;
    private int itemsPerPage = Constants.DEFAULT_PER_PAGE;

    @Inject
    ViewModelFactory viewModelFactory;

    @Inject
    GitHubViewModel gitHubViewModel;

    @Override
    protected int layoutRes() {
        return R.layout.activity_repo_search;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, layoutRes());
        final ArrayList<GitHubResponse.ItemsBean> items =
                getIntent().getParcelableArrayListExtra(Constants.KEY_DATA);

        gitHubViewModel = ViewModelProviders.of(this, viewModelFactory)
                .get(GitHubViewModel.class);

        setObservers(gitHubViewModel.getLoader(), gitHubViewModel.getError());

        gitHubViewModel.getData().observe(
                this, new Observer<RepoData>() {
            @Override
            public void onChanged(RepoData repoData) {
                ArrayList<GitHubResponse.ItemsBean> items = repoData.getItems();
                if(repoAdapter == null) {
                    repoAdapter = new RepoAdapter(items);
                    if(items.size() == itemsPerPage) {
                        binding.listRepo.setupMoreListener(
                                ActivityRepoSearch.this, 1);
                    }
                    LinearLayoutManager layoutManager = new LinearLayoutManager(
                            getApplicationContext());
                    layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
                    binding.listRepo.setLayoutManager(layoutManager);
                    DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(
                            binding.listRepo.getContext(),
                            layoutManager.getOrientation());
                    binding.listRepo.addItemDecoration(dividerItemDecoration);
                    binding.listRepo.setAdapter(repoAdapter);
                }else {
                    if(repoData.getCurrentPage() == 1) {
                        repoAdapter.onRefresh(items);
                    }else {
                        repoAdapter.addMoreItems(items);
                    }
                    if(items.size() < itemsPerPage) {
                        binding.listRepo.removeMoreListener();
                    }else {
                        binding.listRepo.setupMoreListener(
                                ActivityRepoSearch.this, 1);
                    }
                }
            }
        });
        binding.toolbar.inputSearch.setOnKeyListener(this);
        binding.toolbar.inputSearch.addTextChangedListener(this);
        if(items != null) {
            RepoData data = new RepoData();
            data.setCurrentPage(1);
            data.setItems(items);
            gitHubViewModel.getData().setValue(data);
        }
    }

    @Override
    public void afterTextChanged(Editable editable) {
        String query = editable.toString();
        if(query.length() >= 3) {
            search(query);
        }else {
            search("android");
        }
    }

    @Override
    public boolean onKey(View view, int actionId, KeyEvent keyEvent) {
        if(keyEvent.getKeyCode() == KeyEvent.KEYCODE_ENTER) {
            DevUtil.hideKeyboard(binding.listRepo);
            Editable editable = binding.toolbar.inputSearch.getText();
            if(editable != null && !TextUtils.isEmpty(editable.toString())) {
                gitHubViewModel.getLoader().setValue(
                        new ProgressConfig(ProgressType.TRANSPARENT));
                search(query);
            }
        }
        return false;
    }

    private void search(Editable editable) {
        String query;
        if(editable != null) {
            query = editable.toString();
            if(TextUtils.isEmpty(query))
                query = "android";
        }else {
            query = "android";
        }
        search(query);
    }

    private void search(String query) {
        if(TextUtils.isEmpty(query))
            query = "android";
        currentPage = 1;
        this.query = query;
        gitHubViewModel.search(query, currentPage, itemsPerPage);
    }

    @Override
    public void onMoreAsked(int overallItemsCount, int itemsBeforeMore,
                            int maxLastVisiblePosition) {
        currentPage = currentPage + 1;
        gitHubViewModel.search(query, currentPage, itemsPerPage);
    }

    @Override
    protected void showError(String errorMessage, boolean shouldFinish) {
        super.showError(errorMessage, shouldFinish);
        binding.listRepo.hideMoreProgress();
    }

    @Override
    protected void showError(String title, String errorMessage, boolean shouldFinish) {
        super.showError(title, errorMessage, shouldFinish);
        binding.listRepo.hideMoreProgress();
    }

    @Override
    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    @Override
    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

}
