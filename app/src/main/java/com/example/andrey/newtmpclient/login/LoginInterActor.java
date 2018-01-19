package com.example.andrey.newtmpclient.login;

import com.example.andrey.newtmpclient.entities.User;
import com.example.andrey.newtmpclient.login.LoginPresenterImpl;
import com.example.andrey.newtmpclient.network.CheckNetwork;
import com.example.andrey.newtmpclient.network.Client;
import com.example.andrey.newtmpclient.storage.Prefs;
import com.example.andrey.newtmpclient.storage.SHAHashing;

/**
 * Created by andrey on 13.07.2017.
 */

class LoginInterActor  {
    private SHAHashing hashing = new SHAHashing();
    private CheckNetwork checkNetwork = CheckNetwork.instance;
    private LoginPresenterImpl loginPresenter;

    LoginInterActor(LoginPresenterImpl loginPresenter){
        this.loginPresenter = loginPresenter;
        setLoginPwdFromPrefs();
    }

    void checkUser(String login, String pwd) {
        Prefs.addUser(login, pwd);
        User user = new User(login, hashing.hashPwd(pwd));
        loginPresenter.makeNetworkRequestStartAccountActivity(user);
    }

    boolean checkAuth() {
        return Client.INSTANCE.isAuth();
    }

    private void setLoginPwdFromPrefs() {
        User user = Prefs.getUser();
        loginPresenter.fillFields(user.getLogin(), user.getPassword());
    }

    void checkNetwork(boolean isChecked) {
        checkNetwork.setNetworkInsideOrOutside(isChecked);
    }

}
