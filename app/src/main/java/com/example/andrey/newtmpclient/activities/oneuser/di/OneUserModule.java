package com.example.andrey.newtmpclient.activities.oneuser.di;

import dagger.Module;
import dagger.Provides;

import com.example.andrey.newtmpclient.di.base.BaseModule;
import com.example.andrey.newtmpclient.activities.oneuser.OneUserView;
import com.example.andrey.newtmpclient.activities.oneuser.OneUserPresenter;
import com.example.andrey.newtmpclient.activities.oneuser.OneUserInterActor;
import com.example.andrey.newtmpclient.network.TmpService;

@Module
public class OneUserModule implements BaseModule {
    private OneUserView view;

    public OneUserModule(OneUserView view) {
        this.view = view;
    }

    @OneUserScope
    @Provides
    public OneUserPresenter presenter(OneUserInterActor interActor) {
        return new OneUserPresenter(view, interActor);
    }

    @OneUserScope
    @Provides
    OneUserInterActor interActor(TmpService tmpService) {
        return new OneUserInterActor(tmpService);
    }
}

