package com.example.andrey.newtmpclient.activities.maindrawer.di;

import dagger.Module;
import dagger.Provides;

import com.example.andrey.newtmpclient.di.base.BaseModule;
import com.example.andrey.newtmpclient.activities.maindrawer.MainTmpView;
import com.example.andrey.newtmpclient.activities.maindrawer.MainTmpPresenter;
import com.example.andrey.newtmpclient.activities.maindrawer.MainTmpInterActor;
import com.example.andrey.newtmpclient.network.TmpService;

@Module
public class MainTmpModule implements BaseModule {
    private MainTmpView view;

    public MainTmpModule(MainTmpView view) {
        this.view = view;
    }

    @MainTmpScope
    @Provides
    public MainTmpPresenter presenter(MainTmpInterActor interActor) {
        return new MainTmpPresenter(view, interActor);
    }

    @MainTmpScope
    @Provides
    MainTmpInterActor interActor(TmpService tmpService) {
        return new MainTmpInterActor(tmpService);
    }
}

