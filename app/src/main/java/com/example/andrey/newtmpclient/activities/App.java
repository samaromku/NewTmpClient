package com.example.andrey.newtmpclient.activities;

import android.app.Application;

import com.example.andrey.newtmpclient.storage.Prefs;

import io.realm.Realm;
import io.realm.RealmConfiguration;


public class App extends Application{
    @Override
    public void onCreate() {
        super.onCreate();
        configRealm();
        Prefs.init(this);
    }

    private void configRealm() {
        Realm.init(this);
        RealmConfiguration configuration = new RealmConfiguration.Builder()
                .deleteRealmIfMigrationNeeded()
                .build();
        Realm.setDefaultConfiguration(configuration);
    }
}
