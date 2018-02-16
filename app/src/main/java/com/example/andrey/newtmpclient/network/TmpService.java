package com.example.andrey.newtmpclient.network;

import com.example.andrey.newtmpclient.entities.Address;
import com.example.andrey.newtmpclient.entities.Task;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.Body;
import retrofit2.http.POST;

/**
 * Created by savchenko on 19.01.18.
 */

public interface TmpService {
    @POST("/auth")
    Observable<ApiResponse<List<Address>>>getAddresses(@Body Request get_addresses);

    @POST("/auth")
    Observable<Response>logout(@Body Request logout);

    @POST("/auth")
    Observable<Response>createTask(@Body Request createTask);

    @POST("/auth")
    Observable<Response>updateTasks(@Body Request updateTasks);

    @POST("/auth")
    Observable<ApiResponse<List<Task>>>getDoneTasks(@Body Request updateTasks);

    @POST("/auth")
    Observable<ApiResponse<List<Task>>>getNotDoneTasks(@Body Request updateTasks);

    @POST("/auth")
    Observable<Response>updateOneTask(@Body Request updateOneTask);

    @POST("/auth")
    Observable<Response>login(@Body Request login);

    @POST("/auth")
    Observable<Response>getCommentsForTask(@Body Request get_comments);

    @POST("/auth")
    Observable<Response>removeUser(@Body Request remove_user);

    @POST("/auth")
    Observable<Response>createUser(@Body Request create_user);

    @POST("/auth")
    Observable<Response>getUsersCoordinates(@Body Request getUserCoords);

    @POST("/auth")
    Observable<Response>getUsersCoordesPerDay(@Body Request getUserCoords);

    @POST("/auth")
    Observable<Response>updateUserRole(@Body Request updateUserRole);

    @POST("/auth")
    Observable<Response>removeTask(@Body Request removeTask);

    @POST("/auth")
    Observable<Response>changeStatus(@Body Request status);

    @POST("/auth")
    Observable<Response>sendComment(@Body Request comment);

    @POST("/auth")
    Observable<ApiResponse<Boolean>>addFireBaseToken(@Body Request fireBase);


    @POST("/auth")
    Observable<ApiResponse<Boolean>>addCoordinates(@Body Request coordinates);


}
