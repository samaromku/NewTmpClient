package com.example.andrey.newtmpclient.fragments.notdonetasks;


import com.example.andrey.newtmpclient.entities.Task;
import com.example.andrey.newtmpclient.entities.User;
import com.example.andrey.newtmpclient.managers.TasksManager;
import com.example.andrey.newtmpclient.managers.UsersManager;
import com.example.andrey.newtmpclient.network.Request;
import com.example.andrey.newtmpclient.network.Response;
import com.example.andrey.newtmpclient.network.TmpService;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;

public class NotDoneTasksInterActor {
    private static final String TAG = NotDoneTasksInterActor.class.getSimpleName();
    private TmpService tmpService;
    private UsersManager usersManager = UsersManager.INSTANCE;
    private TasksManager tasksManager = TasksManager.INSTANCE;

    public NotDoneTasksInterActor(TmpService tmpService) {
        this.tmpService = tmpService;
    }

    Observable<Response>updateTasks(){
        User user = usersManager.getUser();
        Request request = Request.requestUserWithToken(user, Request.UPDATE_TASKS);
        return tmpService.updateTasks(request);
    }

    Observable<List<Task>>searchedTasks(String search, boolean done){
        return Observable.fromCallable(() -> searchedList(search.split(" "), done));
    }


    private List<Task> searchedList(String[] words, boolean done){
        List<Task>startList = new ArrayList<>();
        if(done) {
            startList.addAll(tasksManager.getDoneTasks());
        }else {
            startList.addAll(tasksManager.getNotDoneTasks());
        }
        for(String word:words) {
            List<Task>orders = new ArrayList<>();
            for (Task task : startList) {
                String bodyAddress = task.getBody().toLowerCase() + task.getAddress().toLowerCase();
                if (bodyAddress.contains(word.toLowerCase())) {
                    orders.add(task);
                }
            }
            startList.clear();
            startList.addAll(orders);
        }
        return startList;
    }
}
