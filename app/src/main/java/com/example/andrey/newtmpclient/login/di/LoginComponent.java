package com.example.andrey.newtmpclient.login.di;

import com.example.andrey.newtmpclient.di.base.BaseComponent;
import com.example.andrey.newtmpclient.di.base.ComponentBuilder;
import com.example.andrey.newtmpclient.login.LoginActivity;

import dagger.Subcomponent;

/**
 * Created by Andrey on 21.01.2018.
 */
@Subcomponent(modules = LoginModule.class)
@LoginScope
public interface LoginComponent extends BaseComponent<LoginActivity>{
    @Subcomponent.Builder
    interface Builder extends ComponentBuilder<LoginComponent, LoginModule>{}
}
