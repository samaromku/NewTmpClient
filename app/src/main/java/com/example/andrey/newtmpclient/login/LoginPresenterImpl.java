package com.example.andrey.newtmpclient.login;

import android.content.Context;
import android.content.Intent;

import com.example.andrey.newtmpclient.activities.maindrawer.MainTmpActivity;
import com.example.andrey.newtmpclient.entities.User;
import com.example.andrey.newtmpclient.network.Request;
import com.example.andrey.newtmpclient.utils.Const;

/**
 * Created by andrey on 13.07.2017.
 */

class LoginPresenterImpl  {
    private LoginView loginView;
    private LoginInterActor loginInterActor;
    private Context context;

    LoginPresenterImpl(LoginView loginView, Context context) {
        this.context = context;
        this.loginView = loginView;
        loginInterActor = new LoginInterActor(this);
    }

    void onDestroy() {
        loginView = null;
    }

    void singIn(String login, String pwd) {
        loginInterActor.checkUser(login, pwd);
    }

    void setChecked(boolean isChecked) {
        loginInterActor.checkNetwork(isChecked);
    }

    void makeNetworkRequestStartAccountActivity(User user) {
//        loginView.startMainActivity();
        Intent intent = new Intent(context, MainTmpActivity.class)
                .putExtra(Const.FROM_AUTH, true);
        new UpdateAuth(context, new Request(user, Request.AUTH), intent).execute();
    }

    void startAccountActivityAfterCheck() {
        if(loginInterActor.checkAuth()){
            context.startActivity(new Intent(context, MainTmpActivity.class));
        }
    }

    void fillFields(String login, String pwd) {
        loginView.setLogin(login);
        loginView.setPwd(pwd);
    }
}
