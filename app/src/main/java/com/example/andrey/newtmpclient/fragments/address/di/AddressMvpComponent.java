package com.example.andrey.newtmpclient.fragments.address.di;

import dagger.Subcomponent;

import com.example.andrey.newtmpclient.fragments.address.AddressMvpFragment;
import com.example.andrey.newtmpclient.di.base.ComponentBuilder;
import com.example.andrey.newtmpclient.di.base.BaseComponent;

@Subcomponent(modules = AddressMvpModule.class)
@AddressMvpScope
public interface AddressMvpComponent extends BaseComponent<AddressMvpFragment> {
    @Subcomponent.Builder
    interface Builder extends ComponentBuilder<AddressMvpComponent, AddressMvpModule> {
    }
}
