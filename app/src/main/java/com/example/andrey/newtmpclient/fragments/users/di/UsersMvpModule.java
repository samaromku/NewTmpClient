package com.example.andrey.newtmpclient.fragments.users.di;

import dagger.Module;
import dagger.Provides;

import com.example.andrey.newtmpclient.di.base.BaseModule;
import com.example.andrey.newtmpclient.fragments.users.UsersMvpView;
import com.example.andrey.newtmpclient.fragments.users.UsersMvpPresenter;
import com.example.andrey.newtmpclient.fragments.users.UsersMvpInterActor;
import com.example.andrey.newtmpclient.managers.UsersManager;

@Module
public class UsersMvpModule implements BaseModule {
    private UsersMvpView view;

    public UsersMvpModule(UsersMvpView view) {
        this.view = view;
    }

    @UsersMvpScope
    @Provides
    public UsersMvpPresenter presenter(UsersMvpInterActor interActor) {
        return new UsersMvpPresenter(view, interActor);
    }

    @UsersMvpScope
    @Provides
    UsersMvpInterActor interActor(UsersManager usersManager) {
        return new UsersMvpInterActor(usersManager);
    }
}

