package com.example.andrey.newtmpclient.fragments.map;

import com.example.andrey.newtmpclient.entities.User;
import com.example.andrey.newtmpclient.rx.TransformerDialog;
import com.example.andrey.newtmpclient.utils.Utils;

import java.util.Date;

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

    void getDirections(User user, String date) {
        interActor.getUsersCoordesPerDay(user, date)
                .compose(new TransformerDialog<>(view))
                .subscribe(response -> {
                            if (!response.getUserCoordsList().isEmpty()) {
                                view.drawDirections(response.getUserCoordsList());
                            } else {
                                view.showToast("Список маршрутов пуст");
                            }
                        }, throwable -> Utils.showError(view, throwable));
    }
}
