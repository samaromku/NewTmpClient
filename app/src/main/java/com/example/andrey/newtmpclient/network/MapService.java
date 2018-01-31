package com.example.andrey.newtmpclient.network;

import com.example.andrey.newtmpclient.entities.map.RouteResponse;

import io.reactivex.Observable;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * Created by savchenko on 31.01.18.
 */

public interface MapService {
    @POST("/maps/api/directions/json")
    Observable<RouteResponse> getDirection(@Query("origin")String origin,
                                           @Query("destination")String destination,
                                           @Query("waypoints")String wayPoints,
                                           @Query("key")String key);
}
