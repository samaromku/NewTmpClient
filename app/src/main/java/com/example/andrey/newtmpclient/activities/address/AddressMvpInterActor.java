package com.example.andrey.newtmpclient.activities.address;

import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

import com.example.andrey.newtmpclient.entities.Address;
import com.example.andrey.newtmpclient.managers.AddressManager;
import com.example.andrey.newtmpclient.managers.TokenManager;
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
        Request request = new Request(GIVE_ME_ADDRESSES_PLEASE);
        request.setToken(TokenManager.instance.getToken());
        return tmpService.getAddresses(request)
                .map(Response::getAddresses);
//        return Observable.fromCallable(() -> addressManager.getAddresses());
    }
}
