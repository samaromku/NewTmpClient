package com.example.andrey.newtmpclient.activities.createuser.di;

import dagger.Module;
import dagger.Provides;

import com.example.andrey.newtmpclient.di.base.BaseModule;
import com.example.andrey.newtmpclient.activities.createuser.CreateNewUserView;
import com.example.andrey.newtmpclient.activities.createuser.CreateNewUserPresenter;
import com.example.andrey.newtmpclient.activities.createuser.CreateNewUserInterActor;

@Module
public class CreateNewUserModule implements BaseModule {
    private CreateNewUserView view;

    public CreateNewUserModule(CreateNewUserView view) {
        this.view = view;
    }

    @CreateNewUserScope
    @Provides
    public CreateNewUserPresenter presenter(CreateNewUserInterActor interActor) {
        return new CreateNewUserPresenter(view, interActor);
    }

    @CreateNewUserScope
    @Provides
    CreateNewUserInterActor interActor() {
        return new CreateNewUserInterActor();
    }
}

