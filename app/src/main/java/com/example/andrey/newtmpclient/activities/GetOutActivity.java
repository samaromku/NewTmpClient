package com.example.andrey.newtmpclient.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;

import com.example.andrey.newtmpclient.login.LoginActivity;

public class GetOutActivity extends AppCompatActivity{
    public void start() {
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
    }
}
