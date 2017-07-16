package com.example.andrey.newtmpclient.login.presenter;

/**
 * Created by andrey on 13.07.2017.
 */

public interface LoginPresenter {
    void onDestroy();
    void singIn(String login, String pwd);
    void setChecked(boolean isChecked);
    void fillFields(String login, String pwd);
}
