package com.devta.headstart.errordialog;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.text.TextUtils;
import android.view.ContextThemeWrapper;

import com.devta.headstart.R;
import com.devta.headstart.base.BaseAlertDialog;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;

/**
 * @author Divyanshu Tayal
 */

public class ErrorDialog extends BaseAlertDialog {

    public static class Builder{

        private final ErrorController.ErrorParams P;

        public Builder(@NonNull Context context){
            this(context, null);
        }

        public Builder(@NonNull Context context, String title){
            this(context, title, null);
        }

        public Builder(@NonNull Context context, String title, String errorMessage){
            this(context, title, errorMessage, null);
        }

        public Builder(@NonNull Context context, String title, String errorMessage, String buttonText){
            this(context, title, errorMessage, buttonText, false);
        }

        public Builder(@NonNull Context context, String title,String errorMessage, String buttonText,
                       boolean isCancelable){
            this(context, title, errorMessage, buttonText, isCancelable, false);
        }

        public Builder(@NonNull Context context, String title, String errorMessage, String buttonText,
                            boolean isCancelable, boolean shouldFinishActivity){
            this.P = new ErrorController.ErrorParams(context, title, errorMessage, buttonText,
                    isCancelable, shouldFinishActivity);
        }

        public Builder setTitle(String title){
            P.title = title;
            return this;
        }

        public Builder setErrorMessage(String errorMessage){
            P.errorMessage = errorMessage;
            return this;
        }

        public Builder setButtonText(String buttonText){
            P.buttonText = buttonText;
            return this;
        }

        public Builder setCancelable(boolean isCancelable){
            P.isCancelable = isCancelable;
            return this;
        }

        public Builder setShouldFinishActivity(boolean shouldFinishActivity){
            P.shouldFinishActivity = shouldFinishActivity;
            return this;
        }

        public ErrorDialog show(){
            return new ErrorDialog(P.context, P.title, P.errorMessage, P.buttonText,
                    P.isCancelable, P.shouldFinishActivity);
        }
    }

    public ErrorDialog(@NonNull final Context context, String title, String errorMessage, String buttonText,
                       boolean isCancelable, final boolean shouldFinishActivity){
        super(context);

        title = TextUtils.isEmpty(title) ? context.getString(R.string.txt_something_went_wrong) :
            title;

        errorMessage = TextUtils.isEmpty(errorMessage) ? context.getString(
                R.string.txt_looks_like_problem)
                : errorMessage;

        buttonText = TextUtils.isEmpty(buttonText) ? context.getString(
                R.string.btn_text_go_back) : buttonText;

        setTitle(title);
        setMessage(errorMessage);
        setCancelable(isCancelable);
        setCanceledOnTouchOutside(isCancelable);

        setButton(BUTTON_POSITIVE, buttonText, new OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dismiss();
                if(shouldFinishActivity) finishActivity(context);
            }
        });

        if(shouldFinishActivity) setOnCancelListener(new OnCancelListener() {
                @Override
                public void onCancel(DialogInterface dialog) {
                    dismiss();
                    finishActivity(context);
                }
        });

        Activity activity = (Activity)context;
        if(!activity.isFinishing() && !isShowing()) this.show();
    }

    private void finishActivity(Context context){
        ((Activity) context).finish();
    }

}
