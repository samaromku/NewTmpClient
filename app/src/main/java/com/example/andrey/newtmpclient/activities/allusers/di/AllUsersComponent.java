package com.example.andrey.newtmpclient.activities.allusers.di;

import dagger.Subcomponent;

import com.example.andrey.newtmpclient.di.base.ComponentBuilder;
import com.example.andrey.newtmpclient.di.base.BaseComponent;
import com.example.andrey.newtmpclient.activities.allusers.AllUsersActivity;

@Subcomponent(modules = AllUsersModule.class)
@AllUsersScope
public interface AllUsersComponent extends BaseComponent<AllUsersActivity> {
    @Subcomponent.Builder
    interface Builder extends ComponentBuilder<AllUsersComponent, AllUsersModule> {
    }
}
