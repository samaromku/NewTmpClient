package com.example.andrey.newtmpclient;

import android.app.Application;

import com.example.andrey.newtmpclient.di.ComponentManager;
import com.example.andrey.newtmpclient.storage.Prefs;

import io.realm.Realm;
import io.realm.RealmConfiguration;


public class App extends Application{
    private static ComponentManager componentManager;

    public static ComponentManager getComponentManager() {
        return componentManager;
    }

    @Override
    public void onCreate() {
        super.onCreate();
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
