package com.example.andrey.newtmpclient.activities.createuser;


import com.example.andrey.newtmpclient.entities.User;
import com.example.andrey.newtmpclient.managers.UserRolesManager;
import com.example.andrey.newtmpclient.managers.UsersManager;
import com.example.andrey.newtmpclient.network.Request;
import com.example.andrey.newtmpclient.network.Response;
import com.example.andrey.newtmpclient.network.TmpService;

import io.reactivex.Completable;
import io.reactivex.Observable;

public class CreateNewUserInterActor {
    private static final String TAG = CreateNewUserInterActor.class.getSimpleName();
    private TmpService tmpService;
    private UserRolesManager userRolesManager = UserRolesManager.INSTANCE;
    private UsersManager usersManager = UsersManager.INSTANCE;

    public CreateNewUserInterActor(TmpService tmpService) {
        this.tmpService = tmpService;
    }

    Observable<Response>createUser(User user){
        return tmpService.createUser(Request.requestUserWithToken(user, Request.ADD_NEW_USER));
    }

    Completable addUser(User user){
        return Completable.fromAction(() -> {
            usersManager.addUser(user);
            userRolesManager.addUserRole(user.getUserRole());
        });
    }
}
