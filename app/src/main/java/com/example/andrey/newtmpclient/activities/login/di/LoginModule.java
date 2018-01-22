package com.example.andrey.newtmpclient.activities.login.di;

import com.example.andrey.newtmpclient.di.base.BaseModule;
import com.example.andrey.newtmpclient.activities.login.LoginInterActor;
import com.example.andrey.newtmpclient.activities.login.LoginPresenterImpl;
import com.example.andrey.newtmpclient.activities.login.LoginView;
import com.example.andrey.newtmpclient.network.TmpService;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Andrey on 21.01.2018.
 */
@Module
public class LoginModule implements BaseModule{
    private LoginView view;

    public LoginModule(LoginView view) {
        this.view = view;
    }

    @Provides
    @LoginScope
    LoginPresenterImpl presenter(LoginInterActor interActor){
        return new LoginPresenterImpl(view, interActor);
    }

    @Provides
    @LoginScope
    LoginInterActor interActor(TmpService tmpService){
        return new LoginInterActor(tmpService);
    }
}
