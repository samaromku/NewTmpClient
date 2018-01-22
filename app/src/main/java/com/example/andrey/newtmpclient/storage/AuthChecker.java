package com.example.andrey.newtmpclient.storage;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import com.example.andrey.newtmpclient.activities.login.LoginActivity;
import com.example.andrey.newtmpclient.network.Client;
import com.example.andrey.newtmpclient.service.GpsService;

import static android.content.ContentValues.TAG;

/**
 * Created by andrey on 16.07.2017.
 */

public class AuthChecker {
    public static void checkAuthAndRedirect(Context context, Intent intent){
        if(Client.INSTANCE.isAuth()) {
            context.startActivity(intent);
        }else {
            Toast.makeText(context, "Вы не авторизованы", Toast.LENGTH_SHORT).show();
            Intent loginIntent = new Intent(context, LoginActivity.class);
            context.startActivity(new Intent(context, LoginActivity.class).addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK));
        }
    }

    public static void checkAuth(Context context){
        if(!Client.INSTANCE.isAuth()) {
            Toast.makeText(context, "Вы не авторизованы", Toast.LENGTH_SHORT).show();
            context.startActivity(new Intent(context, LoginActivity.class));
        }
    }

    public static void checkServerErrorRedirectLoginActivity(Context context){
        Log.i(TAG, "checkServerErrorRedirectLoginActivity: " + Client.INSTANCE.isServerConnection());
        if(!Client.INSTANCE.isServerConnection()) {
            Toast.makeText(context, "Ошибка подключения к серверу", Toast.LENGTH_SHORT).show();
            context.startActivity(new Intent(context, LoginActivity.class).addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK));
            Client.INSTANCE.setAuth(false);
        }
    }

    public static void serverErrorStopService(Context context){
        if(!Client.INSTANCE.isServerConnection()) {
            context.stopService(new Intent(GpsService.newIntent(context)));
            Client.INSTANCE.setAuth(false);
        }
    }


}
