package com.example.andrey.newtmpclient.activities.userrole.di;

import dagger.Subcomponent;

import com.example.andrey.newtmpclient.di.base.ComponentBuilder;
import com.example.andrey.newtmpclient.di.base.BaseComponent;
import com.example.andrey.newtmpclient.activities.userrole.NewUserRoleActivity;

@Subcomponent(modules = NewUserRoleModule.class)
@NewUserRoleScope
public interface NewUserRoleComponent extends BaseComponent<NewUserRoleActivity> {
    @Subcomponent.Builder
    interface Builder extends ComponentBuilder<NewUserRoleComponent, NewUserRoleModule> {
    }
}
