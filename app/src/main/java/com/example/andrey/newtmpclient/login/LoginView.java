package com.example.andrey.newtmpclient.login;

import android.content.Context;

/**
 * Created by andrey on 13.07.2017.
 */

public interface LoginView {
    void setLogin(String login);
    void setPwd(String pwd);
    void startMainActivity();
}
