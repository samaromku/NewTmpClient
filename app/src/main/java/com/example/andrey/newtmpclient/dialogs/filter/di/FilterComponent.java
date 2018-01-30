package com.example.andrey.newtmpclient.dialogs.filter.di;

import dagger.Subcomponent;

import com.example.andrey.newtmpclient.di.base.ComponentBuilder;
import com.example.andrey.newtmpclient.di.base.BaseComponent;
import com.example.andrey.newtmpclient.dialogs.filter.FilterDialog;

@Subcomponent(modules = FilterModule.class)
@FilterScope
public interface FilterComponent extends BaseComponent<FilterDialog> {
    @Subcomponent.Builder
    interface Builder extends ComponentBuilder<FilterComponent, FilterModule> {
    }
}
