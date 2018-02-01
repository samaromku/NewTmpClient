package com.example.andrey.newtmpclient.activities.login;

import com.example.andrey.newtmpclient.network.Client;
import com.example.andrey.newtmpclient.rx.TransformerDialog;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

import static com.example.andrey.newtmpclient.storage.Const.ERROR_DATA;
import static com.example.andrey.newtmpclient.storage.Const.NOT_AUTH;

/**
 * Created by andrey on 13.07.2017.
 */

public class LoginPresenterImpl {
    private LoginView view;
    private LoginInterActor loginInterActor;

    public LoginPresenterImpl(LoginView loginView, LoginInterActor interActor) {
        this.view = loginView;
        this.loginInterActor = interActor;
    }

    void init() {
        loginInterActor.init()
                .subscribe(user -> {
                    view.setLogin(user.getLogin());
                    view.setPwd(user.getPassword());
                });
    }

    void onDestroy() {
        view = null;
    }

    void singIn(String login, String pwd) {
        loginInterActor.checkUser(login, pwd)
                .subscribe(user -> {
                    loginInterActor.makeAuthResponse(user)
                            .compose(new TransformerDialog<>(view))
                            .subscribe(response -> {
                                if(Client.INSTANCE.isAuth()){
                                    view.successAuth();
                                }else {
                                    view.showToast(NOT_AUTH);
                                }
                            }, throwable -> {
                                throwable.printStackTrace();
                                view.showToast(ERROR_DATA);
                            });
                });
    }

    void setChecked(boolean isChecked) {
        loginInterActor.checkNetwork(isChecked);
    }

    void startAccountActivityAfterCheck() {
        if (loginInterActor.checkAuth()) {
            view.startMainActivity(loginInterActor.getFragmentCount());
        }
    }

    void fillFields(String login, String pwd) {
        view.setLogin(login);
        view.setPwd(pwd);
    }
}
