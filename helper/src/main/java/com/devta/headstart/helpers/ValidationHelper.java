package com.devta.headstart.helpers;

import android.text.TextUtils;
import android.util.Patterns;
import android.widget.EditText;

import androidx.annotation.NonNull;

/**
 * @author Divyanshu Tayal
 */

public class ValidationHelper {

    public static boolean isValidEmail(String inputEmail){
        return inputEmail != null && Patterns.EMAIL_ADDRESS.matcher(inputEmail).matches();
    }

    public static boolean isValidPassword(String inputPassword, int minLength, int maxLength){
        return inputPassword != null &&
                (inputPassword.length() >= minLength && inputPassword.length() <= maxLength);
    }

    public static boolean isNull(String input){
        return input == null || input.length() == 0 || input.equals("null") || input.equals("{}");
    }

    public static String optional(String input){
        return isNull(input) ? "null" : input;
    }

    public static String optional(String input, @NonNull String textIfNull){
        return isNull(input) ? textIfNull : input;
    }

    public static boolean isValidInputField(@NonNull EditText inputField){
        return inputField.getText() != null && !isNull(inputField.getText().toString().trim());
    }
}
