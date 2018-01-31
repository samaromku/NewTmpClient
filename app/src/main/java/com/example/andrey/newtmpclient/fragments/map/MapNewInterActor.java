package com.example.andrey.newtmpclient.fragments.map;


import com.example.andrey.newtmpclient.entities.UserCoords;
import com.example.andrey.newtmpclient.entities.map.RouteResponse;
import com.example.andrey.newtmpclient.managers.UserCoordsManager;
import com.example.andrey.newtmpclient.network.MapService;
import com.example.andrey.newtmpclient.network.Request;
import com.example.andrey.newtmpclient.network.Response;
import com.example.andrey.newtmpclient.network.TmpService;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class MapNewInterActor {
    private static final String TAG = MapNewInterActor.class.getSimpleName();
    private TmpService tmpService;
    private MapService mapService;
    private UserCoordsManager userCoordsManager = UserCoordsManager.INSTANCE;

    public MapNewInterActor(TmpService tmpService, MapService mapService) {
        this.tmpService = tmpService;
        this.mapService = mapService;
    }

    Observable<Response> getUsersCoordinates(){
        return tmpService.getUsersCoordinates(Request.requestWithToken(Request.GIVE_ME_LAST_USERS_COORDS));
    }

    Completable addUsersCoordes(List<UserCoords> userCoords){
        return Completable.fromAction(() -> userCoordsManager.addAll(userCoords));
    }

    Observable<RouteResponse>getDirections(){
        return mapService.getDirection(
                "Санкт-Петербург,Итальянская,15",
                "Санкт-Петербург,Куйбышева,10",
                "Санкт-Петербург,Лесной,39|Санкт-Петербург,Просвещения,16|Санкт-Петербург,Харченко,5",
                "AIzaSyDAI8tMCJiA2PQYE9F__J2dmT1APUxTetA")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }
}
