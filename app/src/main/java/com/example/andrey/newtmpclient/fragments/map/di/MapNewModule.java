package com.example.andrey.newtmpclient.fragments.map.di;

import dagger.Module;
import dagger.Provides;

import com.example.andrey.newtmpclient.di.base.BaseModule;
import com.example.andrey.newtmpclient.fragments.map.MapNewView;
import com.example.andrey.newtmpclient.fragments.map.MapNewPresenter;
import com.example.andrey.newtmpclient.fragments.map.MapNewInterActor;
import com.example.andrey.newtmpclient.network.TmpService;

@Module
public class MapNewModule implements BaseModule {
    private MapNewView view;

    public MapNewModule(MapNewView view) {
        this.view = view;
    }

    @MapNewScope
    @Provides
    public MapNewPresenter presenter(MapNewInterActor interActor) {
        return new MapNewPresenter(view, interActor);
    }

    @MapNewScope
    @Provides
    MapNewInterActor interActor(TmpService tmpService) {
        return new MapNewInterActor(tmpService);
    }
}

