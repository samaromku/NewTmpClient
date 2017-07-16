package com.example.andrey.newtmpclient.fragments;

import android.location.Location;
import android.os.Bundle;

import com.example.andrey.newtmpclient.entities.UserCoords;
import com.example.andrey.newtmpclient.managers.UserCoordsManager;
import com.example.andrey.newtmpclient.managers.UsersManager;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.List;

public class MapFragment extends SupportMapFragment {
    private GoogleMap map;
    private UserCoordsManager userCoordsManager = UserCoordsManager.INSTANCE;
    private Location currentLocation = userCoordsManager.getLocation();
    private List<UserCoords>userCoordsList = userCoordsManager.getUserCoordsList();
    UsersManager usersManager = UsersManager.INSTANCE;

    @Override
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);

        getMapAsync(googleMap -> {
            map = googleMap;
            updateUI();
        });
    }

    private void updateUI(){
        if(map==null){
            return;
        }
        map.clear();
        for (int i = 0; i < userCoordsList.size(); i++) {
            UserCoords userCoords = userCoordsList.get(i);
            LatLng point = new LatLng(userCoords.getLat(), userCoords.getLog());
            MarkerOptions mark = new MarkerOptions().position(point).title(usersManager.getUserById(userCoords.getUserId()).getLogin());
            map.addMarker(mark);
        }
        LatLng myPoint = new LatLng(currentLocation.getLatitude(), currentLocation.getLongitude());
        MarkerOptions myMarker = new MarkerOptions()
                .position(myPoint)
                .title(usersManager.getUser().getLogin()+(int)myPoint.latitude+(int)myPoint.longitude);
        map.addMarker(myMarker);

        LatLngBounds bounds = new LatLngBounds.Builder()
                .include(myPoint)
                .build();

        int margin = 30;
        CameraUpdate update = CameraUpdateFactory.newLatLngBounds(bounds, margin);
//        map.animateCamera(update);
        map.setOnMapLoadedCallback(() -> map.moveCamera(update));
    }
}
