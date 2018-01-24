package com.example.andrey.newtmpclient.activities.allusers.di;

import dagger.Module;
import dagger.Provides;

import com.example.andrey.newtmpclient.di.base.BaseModule;
import com.example.andrey.newtmpclient.activities.allusers.AllUsersView;
import com.example.andrey.newtmpclient.activities.allusers.AllUsersPresenter;
import com.example.andrey.newtmpclient.activities.allusers.AllUsersInterActor;

@Module
public class AllUsersModule implements BaseModule {
    private AllUsersView view;

    public AllUsersModule(AllUsersView view) {
        this.view = view;
    }

    @AllUsersScope
    @Provides
    public AllUsersPresenter presenter(AllUsersInterActor interActor) {
        return new AllUsersPresenter(view, interActor);
    }

    @AllUsersScope
    @Provides
    AllUsersInterActor interActor() {
        return new AllUsersInterActor();
    }
}

