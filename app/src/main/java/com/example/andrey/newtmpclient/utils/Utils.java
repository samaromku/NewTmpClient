package com.example.andrey.newtmpclient.utils;

import android.app.Service;
import android.content.Context;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import com.example.andrey.newtmpclient.base.basemvp.BaseView;

import static com.example.andrey.newtmpclient.storage.Const.ERROR_DATA;

/**
 * Created by savchenko on 22.01.18.
 */

public class Utils {
    public static void hideKeyboard(Context context, EditText editText){
        InputMethodManager imm = (InputMethodManager)context.getSystemService(Service.INPUT_METHOD_SERVICE);
        if(imm!=null)
            imm.hideSoftInputFromWindow(editText.getWindowToken(), 0);
    }

    public static void showKeyboard(Context context, EditText editText){
        editText.requestFocus();
        InputMethodManager keyboard = (InputMethodManager)
                context.getSystemService(Context.INPUT_METHOD_SERVICE);
        if(keyboard!=null) {
            keyboard.showSoftInput(editText, 0);
        }
    }

    public static void showError(BaseView view, Throwable throwable){
        throwable.printStackTrace();
        view.showToast(parseThrowable(throwable));
    }

    private static String parseThrowable(@NonNull Throwable throwable) {
        if (!TextUtils.isEmpty(throwable.getLocalizedMessage())) {
            return throwable.getLocalizedMessage();
        }
        if (!TextUtils.isEmpty(throwable.getMessage())) {
            return throwable.getMessage();
        }
        return throwable.toString();
    }
}
