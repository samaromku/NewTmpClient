package com.example.andrey.newtmpclient.activities.maindrawer;


import com.example.andrey.newtmpclient.managers.TokenManager;
import com.example.andrey.newtmpclient.network.Request;
import com.example.andrey.newtmpclient.network.Response;
import com.example.andrey.newtmpclient.network.TmpService;

import io.reactivex.Observable;


public class MainTmpInterActor {
    private static final String TAG = MainTmpInterActor.class.getSimpleName();
    private TmpService tmpService;

    public MainTmpInterActor(TmpService tmpService) {
        this.tmpService = tmpService;
    }

    Observable<Response> logout() {
        Request request = Request.requestWithToken(Request.LOGOUT);
        return tmpService.logout(request);
    }
}
