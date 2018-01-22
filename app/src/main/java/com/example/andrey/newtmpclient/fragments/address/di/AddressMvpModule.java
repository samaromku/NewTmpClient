package com.example.andrey.newtmpclient.fragments.address.di;

import dagger.Module;
import dagger.Provides;

import com.example.andrey.newtmpclient.di.base.BaseModule;
import com.example.andrey.newtmpclient.fragments.address.AddressMvpView;
import com.example.andrey.newtmpclient.fragments.address.AddressMvpPresenter;
import com.example.andrey.newtmpclient.fragments.address.AddressMvpInterActor;
import com.example.andrey.newtmpclient.managers.AddressManager;
import com.example.andrey.newtmpclient.network.TmpService;

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
    AddressMvpInterActor interActor(AddressManager addressManager, TmpService tmpService) {
        return new AddressMvpInterActor(addressManager, tmpService);
    }
}

