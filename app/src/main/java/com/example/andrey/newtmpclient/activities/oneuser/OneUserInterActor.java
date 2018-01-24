package com.example.andrey.newtmpclient.activities.oneuser;


import android.os.UserManager;

import com.example.andrey.newtmpclient.entities.User;
import com.example.andrey.newtmpclient.managers.UsersManager;
import com.example.andrey.newtmpclient.network.Request;
import com.example.andrey.newtmpclient.network.Response;
import com.example.andrey.newtmpclient.network.TmpService;

import io.reactivex.Completable;
import io.reactivex.Observable;

public class OneUserInterActor {
    private static final String TAG = OneUserInterActor.class.getSimpleName();
    private TmpService tmpService;
    private UsersManager usersManager = UsersManager.INSTANCE;

    public OneUserInterActor(TmpService tmpService) {
        this.tmpService = tmpService;
    }

    Observable<Response>removeUser(User user){
        usersManager.setRemoveUser(user);
        return tmpService.removeUser(
                Request.requestUserWithToken(user, Request.REMOVE_USER));
//                .doOnNext(response -> usersManager.removeUser(usersManager.getRemoveUser()));
    }

    Completable setRemoveUser(){
        return Completable.fromAction(() ->
                usersManager.removeUser(usersManager.getRemoveUser()));
    }
}
