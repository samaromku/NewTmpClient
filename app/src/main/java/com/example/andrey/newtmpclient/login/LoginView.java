package com.example.andrey.newtmpclient.login;

import com.example.andrey.newtmpclient.entities.User;

/**
 * Created by andrey on 13.07.2017.
 */

public interface LoginView {
    void setLogin(String login);
    void setPwd(String pwd);
    void startMainActivity();
    void makeAuthResponse(User user);
}
