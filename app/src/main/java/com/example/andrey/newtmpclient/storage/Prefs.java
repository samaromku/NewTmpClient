package com.example.andrey.newtmpclient.storage;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.ActionBar;

import com.example.andrey.newtmpclient.entities.User;

/**
 * Created by andrey on 13.07.2017.
 */

public class Prefs {
    private static SharedPreferences settings;
    private static SharedPreferences.Editor editor;

    @SuppressLint("CommitPrefEdits")
    public static void init(Context context) {
        settings = PreferenceManager.getDefaultSharedPreferences(context.getApplicationContext());
        editor = settings.edit();
    }

    public static void addUser(String login, String pwd){
        editor.putString("login", login);
        editor.putString("password", pwd);
        editor.commit();
    }

    public static User getUser(){
        String login = settings.getString("login", "");
        String pwd = settings.getString("password", "");
        return new User(login, pwd);
    }
}
