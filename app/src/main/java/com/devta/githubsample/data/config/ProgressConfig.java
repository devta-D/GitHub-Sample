package com.devta.githubsample.data.config;

public class ProgressConfig {

    private int progressType;
    private boolean cancelable;

    public ProgressConfig(int progressType) {
        this.progressType = progressType;
    }

    public ProgressConfig(int progressType, boolean cancelable) {
        this.progressType = progressType;
        this.cancelable = cancelable;
    }

    public int getProgressType() {
        return progressType;
    }

    public void setProgressType(int progressType) {
        this.progressType = progressType;
    }

    public boolean isCancelable() {
        return cancelable;
    }

    public void setCancelable(boolean cancelable) {
        this.cancelable = cancelable;
    }
}
