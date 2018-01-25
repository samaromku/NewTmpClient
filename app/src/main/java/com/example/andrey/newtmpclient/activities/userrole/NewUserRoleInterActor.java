package com.example.andrey.newtmpclient.activities.userrole;


import com.example.andrey.newtmpclient.entities.UserRole;
import com.example.andrey.newtmpclient.managers.UserRolesManager;
import com.example.andrey.newtmpclient.network.Request;
import com.example.andrey.newtmpclient.network.Response;
import com.example.andrey.newtmpclient.network.TmpService;

import io.reactivex.Completable;
import io.reactivex.Observable;


public class NewUserRoleInterActor {
    private static final String TAG = NewUserRoleInterActor.class.getSimpleName();
    private TmpService tmpService;
    private UserRolesManager userRolesManager = UserRolesManager.INSTANCE;

    public NewUserRoleInterActor(TmpService tmpService) {
        this.tmpService = tmpService;
    }

    Observable<Response> updateUserRole(UserRole userRole){
         return tmpService.updateUserRole(Request.requestUserRoleWithToken(userRole, Request.CHANGE_PERMISSION_PLEASE));
    }

    Completable setUserRoleUpdate(){
        return Completable.fromAction(() -> userRolesManager.updateUserRole(userRolesManager.getUpdateUserRole()));
    }
}
