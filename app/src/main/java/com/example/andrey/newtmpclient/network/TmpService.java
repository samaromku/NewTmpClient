package com.example.andrey.newtmpclient.network;

import io.reactivex.Observable;
import retrofit2.http.Body;
import retrofit2.http.POST;

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
    Observable<Response>updateTasks(@Body Request updateTasks);

    @POST("/WebApp/auth")
    Observable<Response>updateOneTask(@Body Request updateOneTask);

    @POST("/WebApp/auth")
    Observable<Response>login(@Body Request login);

    @POST("/WebApp/auth")
    Observable<Response>getCommentsForTask(@Body Request get_comments);

    @POST("/WebApp/auth")
    Observable<Response>removeUser(@Body Request remove_user);

    @POST("/WebApp/auth")
    Observable<Response>createUser(@Body Request create_user);

    @POST("/WebApp/auth")
    Observable<Response>getUsersCoordinates(@Body Request getUserCoords);

    @POST("/WebApp/auth")
    Observable<Response>getUsersCoordesPerDay(@Body Request getUserCoords);

    @POST("/WebApp/auth")
    Observable<Response>updateUserRole(@Body Request updateUserRole);

    @POST("/WebApp/auth")
    Observable<Response>removeTask(@Body Request removeTask);

    @POST("/WebApp/auth")
    Observable<Response>changeStatus(@Body Request status);

    @POST("/WebApp/auth")
    Observable<Response>sendComment(@Body Request comment);
}
