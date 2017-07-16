package com.example.andrey.newtmpclient.login.presenter;

import android.content.Context;

import com.example.andrey.newtmpclient.login.model.LoginModel;
import com.example.andrey.newtmpclient.login.model.LoginModelImpl;
import com.example.andrey.newtmpclient.login.view.LoginView;

/**
 * Created by andrey on 13.07.2017.
 */

public class LoginPresenterImpl implements LoginPresenter {
    private LoginView loginView;
    private LoginModel loginModel;

    public LoginPresenterImpl(LoginView loginView, Context context) {
        this.loginView = loginView;
        loginModel = new LoginModelImpl(context, this);
    }

    @Override
    public void onDestroy() {
        loginView = null;
    }

    @Override
    public void singIn(String login, String pwd) {
        loginModel.checkUser(login, pwd);
    }

    @Override
    public void setChecked(boolean isChecked) {
        loginModel.checkNetwork(isChecked);
    }

    @Override
    public void fillFields(String login, String pwd) {
        loginView.setLogin(login);
        loginView.setPwd(pwd);
    }
}
