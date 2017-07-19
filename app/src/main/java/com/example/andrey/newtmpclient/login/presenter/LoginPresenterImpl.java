package com.example.andrey.newtmpclient.login.presenter;

import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.example.andrey.newtmpclient.activities.AccountActivity;
import com.example.andrey.newtmpclient.entities.User;
import com.example.andrey.newtmpclient.login.model.LoginModel;
import com.example.andrey.newtmpclient.login.model.LoginModelImpl;
import com.example.andrey.newtmpclient.login.view.LoginView;
import com.example.andrey.newtmpclient.login.view.UpdateAuth;
import com.example.andrey.newtmpclient.network.Request;
import com.example.andrey.newtmpclient.utils.Const;

import static android.content.ContentValues.TAG;

/**
 * Created by andrey on 13.07.2017.
 */

public class LoginPresenterImpl implements LoginPresenter {
    private LoginView loginView;
    private LoginModel loginModel;
    private Context context;

    public LoginPresenterImpl(LoginView loginView, Context context) {
        this.context = context;
        this.loginView = loginView;
        loginModel = new LoginModelImpl(this);
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
    public void makeNetworkRequestStartAccountActivity(User user) {
        Intent intent = new Intent(context, AccountActivity.class).putExtra(Const.FROM_AUTH, true);
        new UpdateAuth(context, new Request(user, Request.AUTH), intent).execute();
    }

    @Override
    public void startAccountActivityAfterCheck() {
        if(loginModel.checkAuth()){
            context.startActivity(new Intent(context, AccountActivity.class));
        }
    }

    @Override
    public void fillFields(String login, String pwd) {
        loginView.setLogin(login);
        loginView.setPwd(pwd);
    }
}
