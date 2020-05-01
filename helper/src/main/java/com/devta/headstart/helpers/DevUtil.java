package com.devta.headstart.helpers;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import androidx.annotation.NonNull;

import com.devta.headstart.BuildConfig;


/**
 * @author Divyanshu Tayal
 */

public class DevUtil {

    public static boolean isInternetAvailable(Context context) {
        ConnectivityManager cm = (ConnectivityManager)context.getSystemService(
                Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        return (activeNetwork != null && activeNetwork.isConnected());
    }

    public static void hideKeyboard(View view){
        InputMethodManager imm = (InputMethodManager)view.getContext()
                .getSystemService(Context.INPUT_METHOD_SERVICE);
        if(imm!=null)imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    public static void showKeyboard(View view){
        InputMethodManager imm = (InputMethodManager)view.getContext()
                .getSystemService(Context.INPUT_METHOD_SERVICE);
        if(imm!=null)imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, InputMethodManager.HIDE_IMPLICIT_ONLY);
    }

    public static Intent getPlayStoreIntent(){
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setData(Uri.parse("market://details?id=".concat(BuildConfig.APPLICATION_ID)));
        return intent;
    }

    public static float convertPixelsToDp(float px){
        DisplayMetrics metrics = Resources.getSystem().getDisplayMetrics();
        float dp = px / (metrics.densityDpi / 160f);
        return Math.round(dp);
    }

    public static float convertDpToPixel(float dp){
        DisplayMetrics metrics = Resources.getSystem().getDisplayMetrics();
        float px = dp * (metrics.densityDpi / 160f);
        return Math.round(px);
    }

    public static float getScreenWidthInPixels(@NonNull Context context){
        return context.getResources().getDisplayMetrics().widthPixels;
    }

    public static int calItemWidth(@NonNull Context context, @NonNull String operator, float value){
        switch (operator) {
            case "/":
                return (int) (getScreenWidthInPixels(context) / value);
            case "*":
                return (int) (getScreenWidthInPixels(context) * value);
            default:
                return (int) getScreenWidthInPixels(context);
        }
    }

}
