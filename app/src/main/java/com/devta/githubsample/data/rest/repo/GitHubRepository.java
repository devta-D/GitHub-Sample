package com.devta.githubsample.data.rest.repo;

import com.devta.githubsample.data.rest.api.GitHubApi;
import com.devta.githubsample.data.rest.pojo.GitHubResponse;
import com.devta.githubsample.util.UrlFactory;

import javax.inject.Inject;

import io.reactivex.Single;
import io.reactivex.SingleSource;
import io.reactivex.functions.Function;

/**
 * Created on : May, 01, 2020 at 14:32
 * Author     : Divyanshu Tayal
 * Name       : devta-D
 * GitHub     : https://github.com/devta-D/
 * LinkedIn   : https://www.linkedin.com/in/divyanshu-tayal-4a95b2aa/
 */

public class GitHubRepository {

    private final GitHubApi gitHubApi;

    @Inject
    public GitHubRepository(GitHubApi gitHubApi) {
        this.gitHubApi = gitHubApi;
    }

    public Single<GitHubResponse> search(String url) {
        return searchInternally(url, null, 0, 0);
    }

    public Single<GitHubResponse> search(String query, int page, int per_page) {
        return searchInternally(null, query, page, per_page);
    }

    private Single<GitHubResponse> searchInternally(String url, String query,
                                                    int page, int per_page) {
        return url == null
                ? gitHubApi.searchGitHub(UrlFactory.getSearchUrl(query, page, per_page))
                : gitHubApi.searchGitHub(url)
                .flatMap(new Function<GitHubResponse,
                        SingleSource<? extends GitHubResponse>>() {
                    @Override
                    public SingleSource<? extends GitHubResponse>
                    apply(GitHubResponse response) throws Exception {
                        return Single.just(response);
                    }
                });
    }
}
