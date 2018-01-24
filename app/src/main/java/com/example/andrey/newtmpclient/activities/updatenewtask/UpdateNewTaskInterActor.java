package com.example.andrey.newtmpclient.activities.updatenewtask;


import com.example.andrey.newtmpclient.entities.Task;
import com.example.andrey.newtmpclient.managers.TasksManager;
import com.example.andrey.newtmpclient.network.Request;
import com.example.andrey.newtmpclient.network.Response;
import com.example.andrey.newtmpclient.network.TmpService;

import io.reactivex.Completable;
import io.reactivex.Observable;


public class UpdateNewTaskInterActor {
    private static final String TAG = UpdateNewTaskInterActor.class.getSimpleName();
    private TmpService tmpService;
    private TasksManager tasksManager = TasksManager.INSTANCE;

    public UpdateNewTaskInterActor(TmpService tmpService) {
        this.tmpService = tmpService;
    }

    Observable<Response> updateTask(Task task){
        return tmpService.updateOneTask(Request.requestTaskWithToken(task, Request.UPDATE_TASK));
    }

    Completable successUpdate(){
        return Completable.fromAction(() -> tasksManager.updateTask(tasksManager.getTask()));
    }
}
