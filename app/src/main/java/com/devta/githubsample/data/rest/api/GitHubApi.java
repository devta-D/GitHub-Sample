package com.devta.githubsample.data.rest.api;

import com.devta.githubsample.data.rest.pojo.GitHubResponse;

import io.reactivex.Single;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Url;

/**
 * Created on : May, 01, 2020 at 14:20
 * Author     : Divyanshu Tayal
 * Name       : devta-D
 * GitHub     : https://github.com/devta-D/
 * LinkedIn   : https://www.linkedin.com/in/divyanshu-tayal-4a95b2aa/
 */

public interface GitHubApi {

    @GET
    Single<GitHubResponse> searchGitHub(
            @Url String url
    );

    /*@GET("search/repositories?q={query}&sort=asc&page={page}&per_page={per_page}")
    Single<GitHubResponse> searchGitHub(
            @Path("query") String query,
            @Path("page") int pageNum,
            @Path("per_page") int perPage
    );*/
}
