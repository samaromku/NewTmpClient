package com.example.andrey.newtmpclient.activities.login;

import com.example.andrey.newtmpclient.base.basemvp.BaseView;

/**
 * Created by andrey on 13.07.2017.
 */

public interface LoginView extends BaseView{
    void setLogin(String login);
    void setPwd(String pwd);
    void startMainActivity();
    void successAuth();
}
