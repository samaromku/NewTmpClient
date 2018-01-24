package com.example.andrey.newtmpclient.fragments.alltasks;


import com.example.andrey.newtmpclient.entities.Address;
import com.example.andrey.newtmpclient.entities.Task;
import com.example.andrey.newtmpclient.entities.User;
import com.example.andrey.newtmpclient.managers.AddressManager;
import com.example.andrey.newtmpclient.managers.TasksManager;
import com.example.andrey.newtmpclient.managers.UsersManager;
import com.example.andrey.newtmpclient.network.Request;
import com.example.andrey.newtmpclient.network.Response;
import com.example.andrey.newtmpclient.network.TmpService;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Observable;

import static com.example.andrey.newtmpclient.entities.TaskEnum.DONE_TASK;
import static com.example.andrey.newtmpclient.network.Request.requestTaskWithToken;

public class AllTasksInterActor {
    private static final String TAG = AllTasksInterActor.class.getSimpleName();
    private TmpService tmpService;
    private UsersManager usersManager = UsersManager.INSTANCE;
    private TasksManager tasksManager = TasksManager.INSTANCE;
    private AddressManager addressManager = AddressManager.INSTANCE;
    private List<Task>current;
    private Task task;

    public AllTasksInterActor(TmpService tmpService) {
        this.tmpService = tmpService;
    }

    Observable<List<Task>> updateTasks(boolean done) {
        User user = usersManager.getUser();
        Request request = Request.requestUserWithToken(user, Request.UPDATE_TASKS);
        return tmpService.updateTasks(request)
                .map(response -> {
                    List<Task> doneTasks = new ArrayList<>();
                    for (Task task : response.getTaskList()) {
                        if(done) {
                            if (task.getStatus().equals(DONE_TASK)) {
                                doneTasks.add(task);
                            }
                        }else {
                            if (!task.getStatus().equals(DONE_TASK)) {
                                doneTasks.add(task);
                            }
                        }
                    }
                    return doneTasks;
                });
    }

    Observable<List<Task>> searchedTasks(String search, boolean done) {
        return Observable.fromCallable(() -> searchedList(search.split(" "), done));
    }


    private List<Task> searchedList(String[] words, boolean done) {
        List<Task> startList = new ArrayList<>();
        current = new ArrayList<>();

        if (done) {
            startList.addAll(tasksManager.getDoneTasks());
        } else {
            startList.addAll(tasksManager.getNotDoneTasks());
        }
        for (String word : words) {
            List<Task> tasks = new ArrayList<>();
            for (Task task : startList) {
                StringBuilder sb = new StringBuilder();
                if (task.getBody() != null) {
                    sb.append(task.getBody().toLowerCase());
                }
                if (task.getAddress() != null) {
                    sb.append(" ");
                    sb.append(task.getAddress().toLowerCase());
                }
                if (task.getBody() != null && task.getAddress() != null) {
                    String bodyAddress = sb.toString();
                    if (bodyAddress.contains(word.toLowerCase())) {
                        tasks.add(task);
                    }
                }

            }
            startList.clear();
            startList.addAll(tasks);
            current.addAll(tasks);
        }
        return startList;
    }

    Observable<Response>getComments(int position){
        task = current.get(position);
        Request request = requestTaskWithToken(task, Request.WANT_SOME_COMMENTS);
        return tmpService.getCommentsForTask(request);
    }

    int getTaskId(){
        return task.getId();
    }

    Observable<Response> getFirstAddresses(){
        if (addressManager.getAddresses().size() == 0) {
            return tmpService.getAddresses(Request.requestWithToken(Request.GIVE_ME_ADDRESSES_PLEASE));
        }else {
            return Observable.empty();
        }
    }

    Completable setAddresses(List<Address>addresses){
        return Completable.fromAction(() -> addressManager.addAll(addresses));
    }
}
