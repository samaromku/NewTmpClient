package com.example.andrey.newtmpclient.activities.createuser.di;

import dagger.Subcomponent;

import com.example.andrey.newtmpclient.di.base.ComponentBuilder;
import com.example.andrey.newtmpclient.di.base.BaseComponent;
import com.example.andrey.newtmpclient.activities.createuser.CreateNewUserActivity;

@Subcomponent(modules = CreateNewUserModule.class)
@CreateNewUserScope
public interface CreateNewUserComponent extends BaseComponent<CreateNewUserActivity> {
    @Subcomponent.Builder
    interface Builder extends ComponentBuilder<CreateNewUserComponent, CreateNewUserModule> {
    }
}
