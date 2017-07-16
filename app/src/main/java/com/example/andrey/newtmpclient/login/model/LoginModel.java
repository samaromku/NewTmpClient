package com.example.andrey.newtmpclient.login.model;

/**
 * Created by andrey on 13.07.2017.
 */

public interface LoginModel {
    void checkUser(String login, String pwd);
    void setLoginPwdFromPrefs();
    void checkNetwork(boolean isChecked);
}
