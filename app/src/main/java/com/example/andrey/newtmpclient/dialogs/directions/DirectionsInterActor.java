package com.example.andrey.newtmpclient.dialogs.directions;

import android.os.UserManager;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Maybe;
import io.reactivex.Observable;

import com.example.andrey.newtmpclient.entities.User;
import com.example.andrey.newtmpclient.managers.UsersManager;

public class DirectionsInterActor {
    private static final String TAG = DirectionsInterActor.class.getSimpleName();
    private UsersManager usersManager = UsersManager.INSTANCE;

    Observable<List<User>> getListFroAdapter() {
        return Observable.fromCallable(usersManager::getUsers);
    }

    Completable onUserClick(int position) {
        return Completable.fromAction(() -> {
            User selected = usersManager.getUsers().get(position);
            for (User u : usersManager.getUsers()) {
                u.setSelected(false);
            }
            selected.setSelected(true);
        });
    }

    Maybe<User> getSelected(){
        return Maybe.fromCallable(() -> {
            for(User u:usersManager.getUsers()){
                if(u.isSelected()){
                    return u;
                }
            }
            return null;
        });
    }
}
