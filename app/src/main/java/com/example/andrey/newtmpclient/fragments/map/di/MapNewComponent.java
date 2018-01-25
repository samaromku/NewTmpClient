package com.example.andrey.newtmpclient.fragments.map.di;

import dagger.Subcomponent;

import com.example.andrey.newtmpclient.di.base.ComponentBuilder;
import com.example.andrey.newtmpclient.di.base.BaseComponent;
import com.example.andrey.newtmpclient.fragments.map.MapNewFragment;

@Subcomponent(modules = MapNewModule.class)
@MapNewScope
public interface MapNewComponent extends BaseComponent<MapNewFragment> {
    @Subcomponent.Builder
    interface Builder extends ComponentBuilder<MapNewComponent, MapNewModule> {
    }
}
