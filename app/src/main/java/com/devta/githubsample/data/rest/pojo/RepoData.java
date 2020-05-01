package com.devta.githubsample.data.rest.pojo;

import java.util.ArrayList;

/**
 * Created on : May, 01, 2020 at 17:52
 * Author     : Divyanshu Tayal
 * Name       : devta-D
 * GitHub     : https://github.com/devta-D/
 * LinkedIn   : https://www.linkedin.com/in/divyanshu-tayal-4a95b2aa/
 */

public class RepoData {

    private int currentPage;
    private ArrayList<GitHubResponse.ItemsBean> items;

    public int getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(int currentPage) {
        this.currentPage = currentPage;
    }

    public ArrayList<GitHubResponse.ItemsBean> getItems() {
        if(items == null) items = new ArrayList<>();
        return items;
    }

    public void setItems(ArrayList<GitHubResponse.ItemsBean> items) {
        this.items = items;
    }
}
