package com.example.andrey.newtmpclient.activities.userrole.di;

import dagger.Module;
import dagger.Provides;

import com.example.andrey.newtmpclient.di.base.BaseModule;
import com.example.andrey.newtmpclient.activities.userrole.NewUserRoleView;
import com.example.andrey.newtmpclient.activities.userrole.NewUserRolePresenter;
import com.example.andrey.newtmpclient.activities.userrole.NewUserRoleInterActor;
import com.example.andrey.newtmpclient.network.TmpService;

@Module
public class NewUserRoleModule implements BaseModule {
    private NewUserRoleView view;

    public NewUserRoleModule(NewUserRoleView view) {
        this.view = view;
    }

    @NewUserRoleScope
    @Provides
    public NewUserRolePresenter presenter(NewUserRoleInterActor interActor) {
        return new NewUserRolePresenter(view, interActor);
    }

    @NewUserRoleScope
    @Provides
    NewUserRoleInterActor interActor(TmpService tmpService) {
        return new NewUserRoleInterActor(tmpService);
    }
}

