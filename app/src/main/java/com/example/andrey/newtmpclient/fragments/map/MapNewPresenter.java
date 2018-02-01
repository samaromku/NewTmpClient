package com.example.andrey.newtmpclient.fragments.map;

import android.util.Log;

import com.example.andrey.newtmpclient.entities.UserCoords;
import com.example.andrey.newtmpclient.rx.TransformerDialog;
import com.example.andrey.newtmpclient.utils.Utils;

import java.util.List;

public class MapNewPresenter {
    private static final String TAG = MapNewPresenter.class.getSimpleName();
    private MapNewView view;
    private MapNewInterActor interActor;

    public MapNewPresenter(MapNewView view, MapNewInterActor interActor) {
        this.view = view;
        this.interActor = interActor;
    }

    void getUsersCoordes() {
        interActor.getUsersCoordinates()
                .compose(new TransformerDialog<>(view))
                .subscribe(response -> {
                    interActor.addUsersCoordes(response.getUserCoordsList())
                            .subscribe(() -> view.setUserCoordes(response.getUserCoordsList()));
                }, throwable -> Utils.showError(view, throwable));
    }

    void getDirections(){
        interActor.getUsersCoordesPerDay()
                .compose(new TransformerDialog<>(view))
                .subscribe(response ->
                        interActor.getDirections(response.getUserCoordsList())
                        .subscribe(routeResponse -> {
                            view.drawDirections(routeResponse, response.getUserCoordsList());
                            Log.i(TAG, "getDirections: " + routeResponse);
                        }, throwable -> Utils.showError(view, throwable)),
                        throwable -> Utils.showError(view, throwable));
    }
}
