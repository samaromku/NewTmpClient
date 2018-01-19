package com.example.andrey.newtmpclient.activities.maindrawer.di;

import dagger.Subcomponent;

import com.example.andrey.newtmpclient.di.base.ComponentBuilder;
import com.example.andrey.newtmpclient.di.base.BaseComponent;
import com.example.andrey.newtmpclient.activities.maindrawer.MainTmpActivity;

@Subcomponent(modules = MainTmpModule.class)
@MainTmpScope
public interface MainTmpComponent extends BaseComponent<MainTmpActivity> {
    @Subcomponent.Builder
    interface Builder extends ComponentBuilder<MainTmpComponent, MainTmpModule> {
    }
}
