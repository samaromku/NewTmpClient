package com.example.andrey.newtmpclient.fragments.address;

import java.util.List;

import io.reactivex.Observable;

import com.example.andrey.newtmpclient.entities.Address;
import com.example.andrey.newtmpclient.managers.AddressManager;
import com.example.andrey.newtmpclient.network.ApiResponse;
import com.example.andrey.newtmpclient.network.Request;
import com.example.andrey.newtmpclient.network.Response;
import com.example.andrey.newtmpclient.network.TmpService;

import static com.example.andrey.newtmpclient.network.Request.GIVE_ME_ADDRESSES_PLEASE;

public class AddressMvpInterActor {
    private static final String TAG = AddressMvpInterActor.class.getSimpleName();
    private AddressManager addressManager;
    private TmpService tmpService;

    public AddressMvpInterActor(AddressManager addressManager, TmpService tmpService) {
        this.addressManager = addressManager;
        this.tmpService = tmpService;
    }

    Observable<List<Address>> getListFroAdapter() {
        if(addressManager.getAddresses().isEmpty()) {
            Request request = Request.requestWithToken(GIVE_ME_ADDRESSES_PLEASE);
            return tmpService.getAddresses(request)
                    .map(ApiResponse::getData);
        }else {
            return Observable.fromCallable(() -> addressManager.getAddresses());
        }
    }
}
