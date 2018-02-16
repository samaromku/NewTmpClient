package com.example.andrey.newtmpclient.service.gps.di;

import com.example.andrey.newtmpclient.di.base.BaseComponent;
import com.example.andrey.newtmpclient.di.base.ComponentBuilder;
import com.example.andrey.newtmpclient.service.gps.GpsService;

import dagger.Subcomponent;

@Subcomponent(modules = GpsModule.class)
@GpsScope
public interface GpsComponent extends BaseComponent<GpsService> {
    @Subcomponent.Builder
    interface Builder extends ComponentBuilder<GpsComponent, GpsModule> {
    }
}
