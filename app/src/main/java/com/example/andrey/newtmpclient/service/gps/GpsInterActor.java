package com.example.andrey.newtmpclient.service.gps;


import com.example.andrey.newtmpclient.entities.UserCoords;
import com.example.andrey.newtmpclient.network.ApiResponse;
import com.example.andrey.newtmpclient.network.Request;
import com.example.andrey.newtmpclient.network.TmpService;

import io.reactivex.Observable;


public class GpsInterActor {
    private static final String TAG = GpsInterActor.class.getSimpleName();
    private TmpService tmpService;

    public GpsInterActor(TmpService tmpService) {
        this.tmpService = tmpService;
    }

    Observable<ApiResponse<Boolean>> addCoordinates(UserCoords userCoords) {
        return tmpService.addCoordinates(new Request(userCoords, Request.ADD_COORDS));
    }


}
