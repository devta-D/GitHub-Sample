package com.devta.githubsample.util;

import androidx.annotation.NonNull;

/**
 * Created on : May, 01, 2020 at 13:59
 * Author     : Divyanshu Tayal
 * Name       : devta-D
 * GitHub     : https://github.com/devta-D/
 * LinkedIn   : https://www.linkedin.com/in/divyanshu-tayal-4a95b2aa/
 */

public class UrlFactory {

    private static final String BASE_URL = "https://api.github.com/";

    private static final String SEARCH_URL = "search/repositories?q=%s&sort=asc&page=%s&per_page=%s";

    public static String getBaseUrl() {
        return BASE_URL;
    }

    public static String getSearchUrl(@NonNull String searchQuery, int page, int per_page) {
        return String.format(BASE_URL + SEARCH_URL, searchQuery, page, per_page);
    }

}
