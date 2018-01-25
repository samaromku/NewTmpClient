package com.example.andrey.newtmpclient.fragments.map;


import com.example.andrey.newtmpclient.entities.UserCoords;
import com.example.andrey.newtmpclient.managers.UserCoordsManager;
import com.example.andrey.newtmpclient.network.Request;
import com.example.andrey.newtmpclient.network.Response;
import com.example.andrey.newtmpclient.network.TmpService;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Observable;

public class MapNewInterActor {
    private static final String TAG = MapNewInterActor.class.getSimpleName();
    private TmpService tmpService;
    private UserCoordsManager userCoordsManager = UserCoordsManager.INSTANCE;

    public MapNewInterActor(TmpService tmpService) {
        this.tmpService = tmpService;
    }

    Observable<Response> getUsersCoordinates(){
        return tmpService.getUsersCoordinates(Request.requestWithToken(Request.GIVE_ME_LAST_USERS_COORDS));
    }

    Completable addUsersCoordes(List<UserCoords> userCoords){
        return Completable.fromAction(() -> userCoordsManager.addAll(userCoords));
    }
}
