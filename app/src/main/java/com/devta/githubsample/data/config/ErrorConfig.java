package com.devta.githubsample.data.config;

public class ErrorConfig {

    private String title;
    private String message;
    private boolean cancelable;
    private boolean shouldFinishActivity;

    public ErrorConfig(String message) {
        this.message = message;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public boolean isCancelable() {
        return cancelable;
    }

    public void setCancelable(boolean cancelable) {
        this.cancelable = cancelable;
    }

    public boolean isShouldFinishActivity() {
        return shouldFinishActivity;
    }

    public void setShouldFinishActivity(boolean shouldFinishActivity) {
        this.shouldFinishActivity = shouldFinishActivity;
    }
}
