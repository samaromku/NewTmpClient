package com.example.andrey.newtmpclient.activities.address.di;

import dagger.Module;
import dagger.Provides;

import com.example.andrey.newtmpclient.di.base.BaseModule;
import com.example.andrey.newtmpclient.activities.address.AddressMvpView;
import com.example.andrey.newtmpclient.activities.address.AddressMvpPresenter;
import com.example.andrey.newtmpclient.activities.address.AddressMvpInterActor;
import com.example.andrey.newtmpclient.managers.AddressManager;

@Module
public class AddressMvpModule implements BaseModule {
    private AddressMvpView view;

    public AddressMvpModule(AddressMvpView view) {
        this.view = view;
    }

    @AddressMvpScope
    @Provides
    public AddressMvpPresenter presenter(AddressMvpInterActor interActor) {
        return new AddressMvpPresenter(view, interActor);
    }

    @AddressMvpScope
    @Provides
    AddressMvpInterActor interActor(AddressManager addressManager) {
        return new AddressMvpInterActor(addressManager);
    }
}

