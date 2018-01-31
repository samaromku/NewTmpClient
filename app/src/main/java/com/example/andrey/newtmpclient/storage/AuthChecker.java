package com.example.andrey.newtmpclient.storage;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import com.example.andrey.newtmpclient.activities.login.LoginActivity;
import com.example.andrey.newtmpclient.network.Client;
import com.example.andrey.newtmpclient.service.GpsService;

import static android.content.ContentValues.TAG;
import static com.example.andrey.newtmpclient.storage.Const.NOT_AUTH;

/**
 * Created by andrey on 16.07.2017.
 */

public class AuthChecker {

    public static void checkAuth(Context context){
        if(!Client.INSTANCE.isAuth()) {
            Toast.makeText(context, NOT_AUTH, Toast.LENGTH_SHORT).show();
            context.startActivity(new Intent(context, LoginActivity.class));
        }
    }

    public static void serverErrorStopService(Context context){
        if(!Client.INSTANCE.isServerConnection()) {
            context.stopService(new Intent(GpsService.newIntent(context)));
            Client.INSTANCE.setAuth(false);
        }
    }


}
