package com.example.andrey.newtmpclient.activities.oneuser.di;

import dagger.Subcomponent;

import com.example.andrey.newtmpclient.di.base.ComponentBuilder;
import com.example.andrey.newtmpclient.di.base.BaseComponent;
import com.example.andrey.newtmpclient.activities.oneuser.OneUserActivity;

@Subcomponent(modules = OneUserModule.class)
@OneUserScope
public interface OneUserComponent extends BaseComponent<OneUserActivity> {
    @Subcomponent.Builder
    interface Builder extends ComponentBuilder<OneUserComponent, OneUserModule> {
    }
}
