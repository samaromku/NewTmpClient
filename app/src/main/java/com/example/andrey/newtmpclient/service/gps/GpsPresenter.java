package com.example.andrey.newtmpclient.service.gps;

import android.util.Log;

import com.example.andrey.newtmpclient.entities.UserCoords;

public class GpsPresenter {
    private static final String TAG = GpsPresenter.class.getSimpleName();
    private GpsView view;
    private GpsInterActor interActor;

    public GpsPresenter(GpsView view, GpsInterActor interActor) {
        this.view = view;
        this.interActor = interActor;
    }

    void addCoordinates(UserCoords userCoords){
        interActor.addCoordinates(userCoords)
                .subscribe(booleanApiResponse -> Log.i(TAG, "addCoordinates: success add coordinates"),
                        Throwable::printStackTrace);
    }

}
