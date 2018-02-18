package com.example.andrey.newtmpclient.dialogs.directions.di;

import dagger.Subcomponent;

import com.example.andrey.newtmpclient.di.base.ComponentBuilder;
import com.example.andrey.newtmpclient.di.base.BaseComponent;
import com.example.andrey.newtmpclient.dialogs.directions.DirectionsFragment;

@Subcomponent(modules = DirectionsModule.class)
@DirectionsScope
public interface DirectionsComponent extends BaseComponent<DirectionsFragment> {
    @Subcomponent.Builder
    interface Builder extends ComponentBuilder<DirectionsComponent, DirectionsModule> {
    }
}
