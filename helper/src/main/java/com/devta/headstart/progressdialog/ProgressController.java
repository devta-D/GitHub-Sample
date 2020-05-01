package com.devta.headstart.progressdialog;

import android.content.Context;

/**
 * @author Divyanshu Tayal
 */

public class ProgressController {

    public static class ProgressParams{

        final Context context;
        int progressType;
        int progressColor;
        String progressText;
        int progressTextColor;
        boolean cancelable;

        public ProgressParams(Context context, int progressType, int progressColor,
                              String progressText, int progressTextColor, boolean cancelable) {
            this.context = context;
            this.progressColor = progressColor;
            this.progressType = progressType;
            this.progressText = progressText;
            this.progressTextColor = progressTextColor;
            this.cancelable = cancelable;
        }

    }

}
