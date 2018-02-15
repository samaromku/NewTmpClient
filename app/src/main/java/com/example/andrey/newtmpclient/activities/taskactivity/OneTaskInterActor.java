package com.example.andrey.newtmpclient.activities.taskactivity;


import com.example.andrey.newtmpclient.entities.Address;
import com.example.andrey.newtmpclient.entities.Task;
import com.example.andrey.newtmpclient.managers.AddressManager;
import com.example.andrey.newtmpclient.managers.CommentsManager;
import com.example.andrey.newtmpclient.managers.TasksManager;
import com.example.andrey.newtmpclient.managers.UsersManager;
import com.example.andrey.newtmpclient.network.ApiResponse;
import com.example.andrey.newtmpclient.network.Request;
import com.example.andrey.newtmpclient.network.Response;
import com.example.andrey.newtmpclient.network.TmpService;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Observable;

public class OneTaskInterActor {
    private static final String TAG = OneTaskInterActor.class.getSimpleName();
    private TmpService tmpService;
    TasksManager tasksManager = TasksManager.INSTANCE;
    CommentsManager commentsManager = CommentsManager.INSTANCE;
    UsersManager usersManager = UsersManager.INSTANCE;
    private AddressManager addressManager = AddressManager.INSTANCE;


    public OneTaskInterActor(TmpService tmpService) {
        this.tmpService = tmpService;
    }

    Observable<Response>removeTask(int taskId){
        commentsManager.removeAll();
        Task task = tasksManager.getById(taskId);
        tasksManager.setRemoveTask(task);
        return tmpService.removeTask(Request.requestTaskWithToken(task, Request.REMOVE_TASK));
    }

    Completable removeTask(){
        return Completable.fromAction(() -> tasksManager.removeTask(tasksManager.getRemoveTask()));
    }

    Observable<Response>changeStatus(String changedStatusTask, int taskId){
        Task task = tasksManager.getById(taskId);
        task.setUserId(usersManager.getUser().getId());
        task.setStatus(changedStatusTask);
        tasksManager.setTask(task);
        commentsManager.removeAll();
        return tmpService.changeStatus(Request.requestTaskWithToken(task, changedStatusTask));
    }

    Completable updateTask(){
        return Completable.fromAction(() -> tasksManager.updateTask(tasksManager.getTask()));
    }

    Observable<ApiResponse<List<Address>>> getFirstAddresses() {
        return tmpService.getAddresses(Request.requestWithToken(Request.GIVE_ME_ADDRESSES_PLEASE));
    }

    Completable setAddresses(List<Address> addresses) {
        return Completable.fromAction(() -> addressManager.addAll(addresses));
    }
}
