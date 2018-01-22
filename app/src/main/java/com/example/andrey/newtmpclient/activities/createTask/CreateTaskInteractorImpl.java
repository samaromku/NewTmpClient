package com.example.andrey.newtmpclient.activities.createTask;

import android.util.Log;

import com.example.andrey.newtmpclient.entities.Address;
import com.example.andrey.newtmpclient.entities.Task;
import com.example.andrey.newtmpclient.entities.TaskEnum;
import com.example.andrey.newtmpclient.entities.User;
import com.example.andrey.newtmpclient.managers.AddressManager;
import com.example.andrey.newtmpclient.managers.TasksManager;
import com.example.andrey.newtmpclient.managers.UsersManager;
import com.example.andrey.newtmpclient.network.Request;
import com.example.andrey.newtmpclient.network.Response;
import com.example.andrey.newtmpclient.network.TmpService;
import com.example.andrey.newtmpclient.storage.DateUtil;

import java.util.Date;
import java.util.List;

import io.reactivex.Observable;

import static android.content.ContentValues.TAG;

/**
 * Created by andrey on 14.07.2017.
 */

public class CreateTaskInteractorImpl {
    private DateUtil dateUtil = new DateUtil();
    private AddressManager addressManager = AddressManager.INSTANCE;
    private TasksManager tasksManager = TasksManager.INSTANCE;
    private UsersManager usersManager = UsersManager.INSTANCE;
    private String type;
    private String status;
    private String importance;
    private String userName;
    private String date;
    private String body;
    private String address;
    private TmpService tmpService;

    public CreateTaskInteractorImpl(TmpService tmpService) {
        this.tmpService = tmpService;
    }

    String[] getImportanceString() {
        return new String[]{TaskEnum.STANDART, TaskEnum.INFO, TaskEnum.AVARY, TaskEnum.TIME};
    }

    String[] getTypes() {
        return new String[]{TaskEnum.TAKE_INFO, TaskEnum.ARTF, TaskEnum.UUTE, TaskEnum.ITP, TaskEnum.INSPECTION, TaskEnum.APARTMENT};
    }

    String[] getStatuses() {
        return new String[]{TaskEnum.NEW_TASK};
    }

    String[] getUserNames() {
        return new String[]{usersManager.getUsers().get(0).getLogin()};
    }

    String[] getAddressName() {
        List<Address> addresses = addressManager.getAddresses();
        String[] addressNameArray = new String[addresses.size()];
        for (int i = 0; i < addresses.size(); i++) {
            addressNameArray[i] = addresses.get(i).getAddress();
        }
        return addressNameArray;
    }


    public Date getDate() {
        return dateUtil.getDate();
    }

    public Observable<Response> createTask() {
        User user = usersManager.getUserByUserName(userName);
        Address addressEntity = addressManager.getAddressByAddress(address);
        Task task = new Task.Builder()
                .id(tasksManager.getMaxId() + 1)
                .body(body)
                .address(address)
                .orgName(addressEntity.getName())
                .addressId(addressEntity.getId())
                .userId(user.getId())
                .doneTime(date)
                .created(dateUtil.currentDate())
                .importance(importance)
                .type(type)
                .status(status)
                .build();
        Log.i(TAG, "createTask: " + task);
        tasksManager.setTask(task);
        Request request = Request.requestTaskWithToken(task, Request.ADD_TASK_TO_SERVER);
        return tmpService.createTask(request)
                .doOnNext(response -> tasksManager.addTask(tasksManager.getTask()));
    }

    void setTypeSelected(String type) {
        this.type = type;
    }

    void setImportanceSelected(String importance) {
        this.importance = importance;
    }

    void setStatusSelected(String statusSelected) {
        this.status = statusSelected;
    }

    void setUserSelected(String userName) {
        this.userName = userName;
    }


    public void setBody(String body) {
        this.body = body;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
