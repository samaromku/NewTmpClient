package com.example.andrey.newtmpclient.login.model;

import android.content.Context;
import android.content.Intent;

import com.example.andrey.newtmpclient.activities.AccountActivity;
import com.example.andrey.newtmpclient.entities.User;
import com.example.andrey.newtmpclient.login.presenter.LoginPresenter;
import com.example.andrey.newtmpclient.login.view.UpdateAuth;
import com.example.andrey.newtmpclient.network.CheckNetwork;
import com.example.andrey.newtmpclient.network.Client;
import com.example.andrey.newtmpclient.network.Request;
import com.example.andrey.newtmpclient.storage.Prefs;
import com.example.andrey.newtmpclient.storage.SHAHashing;

/**
 * Created by andrey on 13.07.2017.
 */

public class LoginModelImpl implements LoginModel {
    private SHAHashing hashing = new SHAHashing();
    private CheckNetwork checkNetwork = CheckNetwork.instance;
    private LoginPresenter loginPresenter;

    public LoginModelImpl(LoginPresenter loginPresenter){
        this.loginPresenter = loginPresenter;
        setLoginPwdFromPrefs();
    }

    @Override
    public void checkUser(String login, String pwd) {
        Prefs.addUser(login, pwd);
        User user = new User(login, hashing.hashPwd(pwd));
        loginPresenter.makeNetworkRequestStartAccountActivity(user);
    }

    @Override
    public boolean checkAuth() {
        return Client.INSTANCE.isAuth();
    }

    @Override
    public void setLoginPwdFromPrefs() {
        User user = Prefs.getUser();
        loginPresenter.fillFields(user.getLogin(), user.getPassword());
    }

    @Override
    public void checkNetwork(boolean isChecked) {
        checkNetwork.setNetworkInsideOrOutside(isChecked);
    }

}
