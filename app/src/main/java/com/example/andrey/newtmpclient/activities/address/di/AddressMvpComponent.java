package com.example.andrey.newtmpclient.activities.address.di;

import dagger.Subcomponent;

import com.example.andrey.newtmpclient.di.base.ComponentBuilder;
import com.example.andrey.newtmpclient.di.base.BaseComponent;
import com.example.andrey.newtmpclient.activities.address.AddressMvpActivity;

@Subcomponent(modules = AddressMvpModule.class)
@AddressMvpScope
public interface AddressMvpComponent extends BaseComponent<AddressMvpActivity> {
    @Subcomponent.Builder
    interface Builder extends ComponentBuilder<AddressMvpComponent, AddressMvpModule> {
    }
}
