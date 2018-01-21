package com.example.andrey.newtmpclient.login;

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

    void init(){
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
                .subscribe(user -> view.makeAuthResponse(user));
    }

    void setChecked(boolean isChecked) {
        loginInterActor.checkNetwork(isChecked);
    }

    void startAccountActivityAfterCheck() {
        if (loginInterActor.checkAuth()) {
            view.startMainActivity();
        }
    }

    void fillFields(String login, String pwd) {
        view.setLogin(login);
        view.setPwd(pwd);
    }
}
