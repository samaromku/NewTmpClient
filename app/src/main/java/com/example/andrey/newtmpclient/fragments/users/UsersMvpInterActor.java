package com.example.andrey.newtmpclient.fragments.users;



import java.util.List;

import io.reactivex.Observable;

import com.example.andrey.newtmpclient.entities.User;
import com.example.andrey.newtmpclient.managers.UsersManager;

public class UsersMvpInterActor {
    private static final String TAG = UsersMvpInterActor.class.getSimpleName();
    private UsersManager usersManager;

    public UsersMvpInterActor(UsersManager userManager) {
        this.usersManager = userManager;
    }

    Observable<List<User>> getListFroAdapter() {
        return Observable.fromCallable(() -> usersManager.getUsers());
    }
}
