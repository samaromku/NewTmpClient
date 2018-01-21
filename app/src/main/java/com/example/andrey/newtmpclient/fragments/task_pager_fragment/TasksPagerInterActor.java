package com.example.andrey.newtmpclient.fragments.task_pager_fragment;


import com.example.andrey.newtmpclient.entities.User;
import com.example.andrey.newtmpclient.managers.UsersManager;
import com.example.andrey.newtmpclient.network.Request;
import com.example.andrey.newtmpclient.network.Response;
import com.example.andrey.newtmpclient.network.TmpService;

import io.reactivex.Observable;

public class TasksPagerInterActor {
    private static final String TAG = TasksPagerInterActor.class.getSimpleName();
    private TmpService tmpService;
    private UsersManager usersManager = UsersManager.INSTANCE;

    public TasksPagerInterActor(TmpService tmpService) {
        this.tmpService = tmpService;
    }

    Observable<Response>updateTasks(){
        User user = usersManager.getUser();
        Request request = Request.requestUserWithToken(user, Request.UPDATE_TASKS);
        return tmpService.updateTasks(request);
    }
}
