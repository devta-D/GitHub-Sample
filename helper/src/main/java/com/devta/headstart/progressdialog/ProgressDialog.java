package com.devta.headstart.progressdialog;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.devta.headstart.R;

import androidx.annotation.ColorRes;
import androidx.annotation.NonNull;
import androidx.annotation.Size;
import androidx.annotation.StringRes;

/**
 * @author Divyanshu Tayal
 */

public class ProgressDialog extends Dialog {

    public static class Builder{

        private final ProgressController.ProgressParams P;

        public Builder(@NonNull Context context){
            this(context, ProgressType.TRANSPARENT);
        }

        private Builder(@NonNull Context context, int progressType){
            this(context, progressType, R.color.colorProgressDefault);
        }

        private Builder(@NonNull Context context, int progressType, @ColorRes int progressColor){
            this(context, progressType, progressColor, "", R.color.colorProgressDefault);
        }

        private Builder(@NonNull Context context, int progressType, @ColorRes int progressColor,
                       @NonNull String progressText, @ColorRes int progressTextColor){
            this.P = new ProgressController.ProgressParams(context, progressType, progressColor,
                    progressText, progressTextColor, false);
        }

        private Builder(@NonNull Context context, int progressType, @ColorRes int progressColor,
                        @NonNull String progressText, @ColorRes int progressTextColor,
                        boolean cancelable){
            this.P = new ProgressController.ProgressParams(context, progressType, progressColor,
                    progressText, progressTextColor, cancelable);
        }

        public Builder setProgressType(int progressType){
            P.progressType = progressType;
            return this;
        }

        public Builder setCancelable(boolean cancelable){
            P.cancelable = cancelable;
            return this;
        }

        public Builder setProgressColor(@ColorRes int color){
            P.progressColor = color;
            return this;
        }

        public Builder setProgressColor(String color){
            P.progressColor = Color.parseColor(color);
            return this;
        }

        public Builder setProgressText(@NonNull String text){
            P.progressText = text;
            return this;
        }

        public Builder setProgressText(@StringRes int textResId){
            P.progressText = P.context.getString(textResId);
            return this;
        }

        public Builder setProgressTextColor(@ColorRes int color){
            P.progressTextColor = color;
            return this;
        }

        public Builder setProgressTextColor(@Size(min = 7, max = 7) String color){
            P.progressTextColor = Color.parseColor(color);
            return this;
        }

        public ProgressDialog create(){
            return new ProgressDialog(P.context, P.progressType, P.progressColor,
                    P.progressText, P.progressTextColor, P.cancelable);
        }

    }

    private int progressType;
    private int progressColor;
    private int progressTextColor;
    private String progressText;
    private Context context;
    private boolean cancelable;

    public ProgressDialog(Context context, int progressType, int progressColor,
                          String progressText, int progressTextColor, boolean cancelable) {
        super(context);
        this.context = context;
        this.progressType = progressType;
        this.progressColor = progressColor;
        this.progressText = progressText;
        this.progressTextColor = progressTextColor;
        this.cancelable = cancelable;
        init();
    }

    private void init(){

        View contentView;
        LayoutInflater inflater = LayoutInflater.from(getContext());

        if(progressType == ProgressType.FULLSCREEN){

            contentView = inflater.inflate(R.layout.layout_progress_fullscreen, null);

        }else if(progressType == ProgressType.CENTERED){

            contentView = inflater.inflate(R.layout.layout_progress_centered, null);

        }else {
            contentView = inflater.inflate(R.layout.layout_progress_transparent, null);
        }

        setContentView(contentView);

        ProgressBar progressBar = contentView.findViewById(R.id.progressbar);
        if(progressBar.getIndeterminateDrawable() != null)
            progressBar.getIndeterminateDrawable().setColorFilter(progressColor, PorterDuff.Mode.SRC_IN);

        TextView progressTextView = contentView.findViewById(R.id.progressText);
        if(!TextUtils.isEmpty(progressText)){
            progressTextView.setText(progressText);
            progressTextView.setTextColor(progressTextColor);
            progressTextView.setVisibility(View.VISIBLE);
        }else {
            progressTextView.setVisibility(View.GONE);
        }

        if(getWindow() != null){
            getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
            if(progressType == ProgressType.FULLSCREEN)
                getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT,
                        WindowManager.LayoutParams.MATCH_PARENT);
        }

        setCancelable(cancelable);
        setCanceledOnTouchOutside(cancelable);
    }

    @Override
    public void show() {
        if(context instanceof Activity){
            Activity activity = (Activity)context;
            if(activity.isFinishing()) return;
        }
        super.show();
    }

    @Override
    public void dismiss() {
        if(context instanceof Activity){
            Activity activity = (Activity)context;
            if(activity.isFinishing()) return;
        }
        super.dismiss();
    }
}
