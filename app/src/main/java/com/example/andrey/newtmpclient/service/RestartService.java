package com.example.andrey.newtmpclient.service;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.support.annotation.RequiresApi;

import com.example.andrey.newtmpclient.service.period.PeriodService;

/**
 * Created by Andrey on 28.03.2018.
 */

public class RestartService extends BroadcastReceiver {

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    public void onReceive(Context context, Intent intent) {
        context.startService(PeriodService.newIntent(context));
    }
}
