package com.example.andrey.newtmpclient.login.model;

import android.content.Context;
import android.content.Intent;

import com.example.andrey.newtmpclient.activities.AccountActivity;
import com.example.andrey.newtmpclient.entities.User;
import com.example.andrey.newtmpclient.login.presenter.LoginPresenter;
import com.example.andrey.newtmpclient.login.view.UpdateAuth;
import com.example.andrey.newtmpclient.network.CheckNetwork;
import com.example.andrey.newtmpclient.network.Request;
import com.example.andrey.newtmpclient.storage.Prefs;
import com.example.andrey.newtmpclient.storage.SHAHashing;

/**
 * Created by andrey on 13.07.2017.
 */

public class LoginModelImpl implements LoginModel {
    private SHAHashing hashing = new SHAHashing();
    private CheckNetwork checkNetwork = CheckNetwork.instance;
    private Context context;
    private LoginPresenter loginPresenter;

    public LoginModelImpl(Context context, LoginPresenter loginPresenter){
        this.context = context;
        this.loginPresenter = loginPresenter;
        setLoginPwdFromPrefs();
    }

    @Override
    public void checkUser(String login, String pwd) {
        Prefs.addUser(login, pwd);
        User user = new User(login, hashing.hashPwd(pwd));
        makeNetworkRequest(user);
    }

    private void makeNetworkRequest(User user){
        Intent intent = new Intent(context, AccountActivity.class).putExtra("fromAuth", true);
        new UpdateAuth(context, new Request(user, Request.AUTH), intent).execute();
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
