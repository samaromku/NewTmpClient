package com.example.andrey.newtmpclient.fragments.users.di;

import dagger.Subcomponent;

import com.example.andrey.newtmpclient.di.base.ComponentBuilder;
import com.example.andrey.newtmpclient.di.base.BaseComponent;
import com.example.andrey.newtmpclient.fragments.users.UsersMvpFragment;

@Subcomponent(modules = UsersMvpModule.class)
@UsersMvpScope
public interface UsersMvpComponent extends BaseComponent<UsersMvpFragment> {
    @Subcomponent.Builder
    interface Builder extends ComponentBuilder<UsersMvpComponent, UsersMvpModule> {
    }
}
