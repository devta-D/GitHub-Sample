package com.devta.headstart.errordialog;

import android.content.Context;

/**
 * @author Divyanshu Tayal
 */
public class ErrorController {

    public static class ErrorParams{

        final Context context;
        String title;
        String errorMessage;
        String buttonText;
        boolean isCancelable;
        boolean shouldFinishActivity;

        public ErrorParams(Context context, String title, String errorMessage, String buttonText,
                           boolean isCancelable, boolean shouldFinishActivity) {
            this.context = context;
            this.title = title;
            this.errorMessage = errorMessage;
            this.buttonText = buttonText;
            this.isCancelable = isCancelable;
            this.shouldFinishActivity = shouldFinishActivity;
        }
    }

}
