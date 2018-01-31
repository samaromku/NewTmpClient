package com.example.andrey.newtmpclient;

import android.app.Application;

import com.example.andrey.newtmpclient.di.ComponentManager;
import com.example.andrey.newtmpclient.storage.Prefs;
import com.crashlytics.android.Crashlytics;
import io.fabric.sdk.android.Fabric;

import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.victoralbertos.rx2_permissions_result.RxPermissionsResult;


public class App extends Application{
    private static ComponentManager componentManager;

    public static ComponentManager getComponentManager() {
        return componentManager;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        RxPermissionsResult.register(this);
        Fabric.with(this, new Crashlytics());
        configRealm();
        Prefs.init(this);
        componentManager = new ComponentManager();
        componentManager.init();
    }

    private void configRealm() {
        Realm.init(this);
        RealmConfiguration configuration = new RealmConfiguration.Builder()
                .deleteRealmIfMigrationNeeded()
                .build();
        Realm.setDefaultConfiguration(configuration);
    }
}
