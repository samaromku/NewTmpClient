package com.example.andrey.newtmpclient.network;

import com.example.andrey.newtmpclient.entities.Address;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

/**
 * Created by savchenko on 19.01.18.
 */

public interface TmpService {
    @POST("/WebApp/auth")
    Observable<Response>getAddresses(@Body Request get_addresses);

    @POST("/WebApp/auth")
    Observable<Response>logout(@Body Request logout);

    @POST("/WebApp/auth")
    Observable<Response>createTask(@Body Request createTask);

    @POST("/WebApp/auth")
    Observable<Response>updateTasks(@Body Request createTask);

    @POST("/WebApp/auth")
    Observable<Response>login(@Body Request login);

}
