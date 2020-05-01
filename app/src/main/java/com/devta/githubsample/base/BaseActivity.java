package com.devta.githubsample.base;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.StringRes;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;

import com.devta.githubsample.BuildConfig;
import com.devta.githubsample.R;
import com.devta.githubsample.data.config.ErrorConfig;
import com.devta.githubsample.data.config.ProgressConfig;
import com.devta.headstart.errordialog.ErrorDialog;
import com.devta.headstart.helpers.DevUtil;
import com.devta.headstart.nointernetdialog.NoInternetDialog;
import com.devta.headstart.progressdialog.ProgressDialog;

import java.util.ArrayList;

import dagger.android.support.DaggerAppCompatActivity;

public abstract class BaseActivity extends DaggerAppCompatActivity
        implements View.OnClickListener {

    protected ProgressDialog progressDialog;
    protected ArrayList<ErrorDialog> listErrorDialog = new ArrayList<>();
    protected ArrayList<NoInternetDialog> listNoInternetDialog = new ArrayList<>();

    @LayoutRes
    protected abstract int layoutRes();

    protected void setObservers(LiveData<ProgressConfig> loading, LiveData<ErrorConfig> error) {
        setErrorObserver(error);
        setLoadingObserver(loading);
    }

    protected void setLoadingObserver(LiveData<ProgressConfig> loadingObserver) {
        if(loadingObserver == null) return;
        loadingObserver.observe(this, new Observer<ProgressConfig>() {
            @Override
            public void onChanged(ProgressConfig progressConfig) {
                if(progressConfig != null)
                    showProgress(progressConfig.getProgressType(), progressConfig.isCancelable());
                else
                    dismissProgress();
            }
        });
    }

    protected void setErrorObserver(LiveData<ErrorConfig> errorObserver) {
        if(errorObserver == null) return;
        errorObserver.observe(this, new Observer<ErrorConfig>() {
            @Override
            public void onChanged(ErrorConfig errorConfig) {
                showError(errorConfig.getMessage(), errorConfig.isShouldFinishActivity());
            }
        });
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(layoutRes());
    }

    protected void makeToast(@NonNull String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    protected void makeToast(@StringRes int message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    protected void makeLongToast(@NonNull String message) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show();
    }

    protected void makeLongToast(@StringRes int message) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show();
    }

    protected void showProgress(int progressType) {
        showProgress(progressType, false);
    }

    protected void showProgress(int progressType, boolean cancelable) {
        dismissProgress();
        progressDialog = new ProgressDialog.Builder(this)
                .setProgressType(progressType)
                .setProgressColor("#FF8BC149")
                .setCancelable(cancelable)
                .create();
        progressDialog.show();
    }

    protected boolean isProgressShowing() {
        return progressDialog != null && progressDialog.isShowing();
    }

    protected void dismissProgress() {
        if (progressDialog != null && progressDialog.isShowing()) progressDialog.dismiss();
    }

    public void onInternetNotFound(boolean retry){
        for(NoInternetDialog noInternetDialog : listNoInternetDialog){
            noInternetDialog.dismiss();
        }
        listNoInternetDialog.add(new NoInternetDialog.Builder(this)
                .setCallbacks(null)
                .setRetryAvailable(retry)
                .show());
    }

    protected void showError(String errorMessage, boolean shouldFinish){
        if(BuildConfig.DEBUG && !TextUtils.isEmpty(errorMessage)) Log.e("ErrorConfig in API", errorMessage);
        dismissProgress();
        for(ErrorDialog errorDialog : listErrorDialog){
            errorDialog.dismiss();
        }
        listErrorDialog.add(new ErrorDialog.Builder(this)
                .setErrorMessage(errorMessage)
                .setShouldFinishActivity(shouldFinish)
                .show());
    }

    protected void showError(String title, String errorMessage, boolean shouldFinish){
        if(BuildConfig.DEBUG && !TextUtils.isEmpty(errorMessage)) Log.e("ErrorConfig in API", errorMessage);
        dismissProgress();
        for(ErrorDialog errorDialog : listErrorDialog){
            errorDialog.dismiss();
        }
        listErrorDialog.add(new ErrorDialog.Builder(this)
                .setTitle(title)
                .setErrorMessage(errorMessage)
                .setShouldFinishActivity(shouldFinish)
                .show());
    }

    public void onSessionExpire() {
        dismissProgress();
    }

    @Override
    public void onClick(View v) {
        DevUtil.hideKeyboard(v);
    }

    public void onInternetDialogButtonClick(int buttonType) {}

    public void setTitle(@NonNull String title) {
        TextView titleTV = findViewById(R.id.title);
        if(titleTV != null) titleTV.setText(title);
    }

    @Override
    public void setTitle(CharSequence title) {
        setTitle(title.toString());
    }

    @Override
    public void setTitle(@StringRes int titleId) {
        setTitle(getString(titleId));
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}
