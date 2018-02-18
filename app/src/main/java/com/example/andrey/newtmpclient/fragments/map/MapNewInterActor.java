package com.example.andrey.newtmpclient.fragments.map;


import com.example.andrey.newtmpclient.entities.User;
import com.example.andrey.newtmpclient.entities.UserCoords;
import com.example.andrey.newtmpclient.entities.map.RouteResponse;
import com.example.andrey.newtmpclient.managers.UserCoordsManager;
import com.example.andrey.newtmpclient.network.MapService;
import com.example.andrey.newtmpclient.network.Request;
import com.example.andrey.newtmpclient.network.Response;
import com.example.andrey.newtmpclient.network.TmpService;

import java.util.Date;
import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

import static com.example.andrey.newtmpclient.network.Request.GET_USER_COORDES_PER_DAY;

public class MapNewInterActor {
    private static final String TAG = MapNewInterActor.class.getSimpleName();
    private TmpService tmpService;
    private MapService mapService;
    private UserCoordsManager userCoordsManager = UserCoordsManager.INSTANCE;

    public MapNewInterActor(TmpService tmpService, MapService mapService) {
        this.tmpService = tmpService;
        this.mapService = mapService;
    }

    Observable<Response> getUsersCoordinates() {
        return tmpService.getUsersCoordinates(Request.requestWithToken(Request.GIVE_ME_LAST_USERS_COORDS));
    }

    Observable<Response> getUsersCoordesPerDay(User user, Date date) {
        return tmpService.getUsersCoordesPerDay(Request.
                requestUserIdWithDateWithToken(user.getId(), date, GET_USER_COORDES_PER_DAY));
    }

    Completable addUsersCoordes(List<UserCoords> userCoords) {
        return Completable.fromAction(() -> userCoordsManager.addAll(userCoords));
    }

    String testCoordes = "{\"response\":\"add_coordes_per_day\",\"userCoordsList\":[{\"id\":8688,\"userId\":186,\"lat\":60.22073982,\"log\":30.32309633,\"ts\":\"31-01-18 07:45\"},{\"id\":8689,\"userId\":186,\"lat\":60.218696,\"log\":30.3083303,\"ts\":\"31-01-18 07:50\"},{\"id\":8690,\"userId\":186,\"lat\":60.13757051,\"log\":30.38033368,\"ts\":\"31-01-18 07:55\"},{\"id\":8691,\"userId\":186,\"lat\":60.218696,\"log\":30.3083303,\"ts\":\"31-01-18 08:00\"},{\"id\":8692,\"userId\":186,\"lat\":60.06676873,\"log\":30.34539298,\"ts\":\"31-01-18 08:05\"},{\"id\":8693,\"userId\":186,\"lat\":60.05870486,\"log\":30.33952696,\"ts\":\"31-01-18 08:10\"},{\"id\":8694,\"userId\":186,\"lat\":60.0454423,\"log\":30.3293092,\"ts\":\"31-01-18 08:15\"},{\"id\":8695,\"userId\":186,\"lat\":60.03582509,\"log\":30.32140783,\"ts\":\"31-01-18 08:20\"},{\"id\":8734,\"userId\":186,\"lat\":59.9311822,\"log\":30.3355223,\"ts\":\"31-01-18 10:32\"},{\"id\":8735,\"userId\":186,\"lat\":59.931202,\"log\":30.3355201,\"ts\":\"31-01-18 10:35\"},{\"id\":8736,\"userId\":186,\"lat\":59.9311306,\"log\":30.3355647,\"ts\":\"31-01-18 10:40\"},{\"id\":8737,\"userId\":186,\"lat\":59.9311684,\"log\":30.3356193,\"ts\":\"31-01-18 10:45\"},{\"id\":8738,\"userId\":186,\"lat\":59.9311988,\"log\":30.3355198,\"ts\":\"31-01-18 10:50\"},{\"id\":8739,\"userId\":186,\"lat\":59.9311555,\"log\":30.33559,\"ts\":\"31-01-18 10:55\"},{\"id\":8740,\"userId\":186,\"lat\":59.931202,\"log\":30.3355201,\"ts\":\"31-01-18 11:00\"},{\"id\":8741,\"userId\":186,\"lat\":59.9311984,\"log\":30.3355205,\"ts\":\"31-01-18 11:05\"},{\"id\":8742,\"userId\":186,\"lat\":59.9311976,\"log\":30.3355235,\"ts\":\"31-01-18 11:10\"},{\"id\":8743,\"userId\":186,\"lat\":59.931202,\"log\":30.3355201,\"ts\":\"31-01-18 11:15\"},{\"id\":8744,\"userId\":186,\"lat\":59.9311974,\"log\":30.3355236,\"ts\":\"31-01-18 11:25\"},{\"id\":8745,\"userId\":186,\"lat\":59.931202,\"log\":30.3355201,\"ts\":\"31-01-18 11:30\"},{\"id\":8746,\"userId\":186,\"lat\":59.9311535,\"log\":30.3355848,\"ts\":\"31-01-18 11:36\"},{\"id\":8747,\"userId\":186,\"lat\":59.931202,\"log\":30.3355201,\"ts\":\"31-01-18 11:40\"},{\"id\":8748,\"userId\":186,\"lat\":59.9314891,\"log\":30.3362766,\"ts\":\"31-01-18 11:45\"},{\"id\":8749,\"userId\":186,\"lat\":59.9325082,\"log\":30.3345225,\"ts\":\"31-01-18 11:50\"},{\"id\":8750,\"userId\":186,\"lat\":59.9342501,\"log\":30.3332154,\"ts\":\"31-01-18 11:55\"},{\"id\":8751,\"userId\":186,\"lat\":59.9441505,\"log\":30.2901415,\"ts\":\"31-01-18 12:00\"}]}";
    Observable<RouteResponse> getDirections(List<UserCoords> userCoordes) {
        StringBuilder sb = new StringBuilder();
        for (int i = 5; i < userCoordes.size(); i++) {
            UserCoords userCoord = userCoordes.get(i);
            if (i == 5) {
                sb
                        .append("via:")
                        .append(userCoord.getLat())
                        .append(",")
                        .append(userCoord.getLog());
            } else {
                sb
                        .append("|via:")
                        .append(userCoord.getLat())
                        .append(",")
                        .append(userCoord.getLog());
            }
        }
        UserCoords originUserCoords = userCoordes.get(0);
        UserCoords destUserCoords = userCoordes.get(userCoordes.size()-1);
        return mapService.getDirection(
                originUserCoords.getLat() + "," + originUserCoords.getLog(),
                destUserCoords.getLat() + "," + destUserCoords.getLog(),
//                "Санкт-Петербург,Лесной,39|Санкт-Петербург,Просвещения,16|Санкт-Петербург,Харченко,5",
                sb.toString(),
//                "via:60.22073982,30.32309633|via:60.06676873,30.34539298",
                "AIzaSyDAI8tMCJiA2PQYE9F__J2dmT1APUxTetA")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }
}
