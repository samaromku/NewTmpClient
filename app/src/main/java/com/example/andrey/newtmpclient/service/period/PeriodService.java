package com.example.andrey.newtmpclient.service.period;

import android.app.AlarmManager;
import android.app.IntentService;
import android.app.PendingIntent;
import android.app.Service;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.SystemClock;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.util.Log;

import com.example.andrey.newtmpclient.App;
import com.example.andrey.newtmpclient.service.period.di.PeriodComponent;
import com.example.andrey.newtmpclient.service.period.di.PeriodModule;

import javax.inject.Inject;

public class PeriodService extends IntentService implements PeriodView {
    private static final String TAG = PeriodService.class.getSimpleName();
    @Inject
    PeriodPresenter presenter;

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    public static Intent newIntent(Context context) {
        return new Intent(context, PeriodService.class)
                .addFlags(Intent.FLAG_RECEIVER_FOREGROUND);
    }

    public static void setAlarmManger(Context context){
        Intent i = newIntent(context);
        PendingIntent pi = PendingIntent.getService(context, 0, i, 0);
        AlarmManager am = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        if(am!=null)
        am.setRepeating(AlarmManager.RTC_WAKEUP, SystemClock.elapsedRealtime(), 60000, pi);
    }

    @Override
    public int onStartCommand(@Nullable Intent intent, int flags, int startId) {
        Log.i(TAG, "service onStartCommand: ");
        return Service.START_NOT_STICKY;
    }

    public PeriodService() {
        super(TAG);
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        Log.i(TAG, "service onHandleIntent: ");
    }

    @Override
    public void onCreate() {
        super.onCreate();
        ((PeriodComponent) App.getComponentManager()
                .getPresenterComponent(getClass(), new PeriodModule(this))).inject(this);
    }

    @Override
    public ComponentName startForegroundService(Intent service) {
        Log.i(TAG, "startForegroundService: ");
        return super.startForegroundService(service);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        App.getComponentManager().releaseComponent(getClass());
        Log.i(TAG, "onDestroy: ");
        Intent restartService = new Intent("PeriodService");
        sendBroadcast(restartService);
    }
}
