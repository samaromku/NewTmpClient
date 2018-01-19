package com.example.andrey.newtmpclient.activities.address;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;

import com.example.andrey.newtmpclient.entities.Address;
import com.example.andrey.newtmpclient.managers.AddressManager;

public class AddressMvpInterActor {
    private static final String TAG = AddressMvpInterActor.class.getSimpleName();
    private AddressManager addressManager;

    public AddressMvpInterActor(AddressManager addressManager) {
        this.addressManager = addressManager;
    }

    Observable<List<Address>> getListFroAdapter() {
        return Observable.fromCallable(() -> addressManager.getAddresses());
    }
}
