package com.example.andrey.newtmpclient.service.gps.di;

import com.example.andrey.newtmpclient.di.base.BaseModule;
import com.example.andrey.newtmpclient.network.TmpService;
import com.example.andrey.newtmpclient.service.gps.GpsInterActor;
import com.example.andrey.newtmpclient.service.gps.GpsPresenter;
import com.example.andrey.newtmpclient.service.gps.GpsView;

import dagger.Module;
import dagger.Provides;

@Module
public class GpsModule implements BaseModule {
    private GpsView view;

    public GpsModule(GpsView view) {
        this.view = view;
    }

    @GpsScope
    @Provides
    public GpsPresenter presenter(GpsInterActor interActor) {
        return new GpsPresenter(view, interActor);
    }

    @GpsScope
    @Provides
    GpsInterActor interActor(TmpService tmpService) {
        return new GpsInterActor(tmpService);
    }
}

