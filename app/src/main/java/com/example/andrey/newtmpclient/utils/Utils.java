package com.example.andrey.newtmpclient.utils;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Service;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.speech.RecognizerIntent;
import android.support.v7.app.AppCompatActivity;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import com.example.andrey.newtmpclient.R;
import com.example.andrey.newtmpclient.base.basemvp.BaseView;

import static com.example.andrey.newtmpclient.fragments.one_task_fragment.OneTaskPageFragment.VOICE_RECOGNITION_REQUEST_CODE;
import static com.example.andrey.newtmpclient.storage.Const.AUTH_FAILED;
import static com.example.andrey.newtmpclient.storage.Const.RESPONSE_FAILED;

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
        if(throwable.getMessage().equals(AUTH_FAILED)){
            view.notSuccessAuth();
        }else if(throwable.getMessage().equals(RESPONSE_FAILED)){
            view.showToast("Ошибка запроса");
        }
    }

    public static void showDialog(Context context,
                                  String title,
                                  String message,
                                  DialogInterface.OnClickListener onClickListener){
        AlertDialog dialog = new AlertDialog.Builder(context)
                .setTitle(title)
                .setMessage(message)
                .setPositiveButton(R.string.ok, onClickListener)
                .setNegativeButton(R.string.cancel, null).create();
        dialog.setOnShowListener(dialogInterface -> {
            dialog.getButton(AlertDialog.BUTTON_NEGATIVE).setTextColor(context.getResources().getColor(R.color.colorPrimaryDark));
            dialog.getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(context.getResources().getColor(R.color.colorPrimaryDark));
        });
        dialog.show();
    }

    public static void startInputVoice(Activity activity) {
        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        intent.putExtra(RecognizerIntent.EXTRA_PROMPT,
                "Скажите что-нибудь");
        try {
            activity.startActivityForResult(intent, VOICE_RECOGNITION_REQUEST_CODE);
        } catch (ActivityNotFoundException e) {
            Intent browserIntent = new Intent(Intent.ACTION_VIEW,   Uri.parse("https://market.android.com/details?id=APP_PACKAGE_NAME"));
            activity.startActivity(browserIntent);
        }
    }
}
