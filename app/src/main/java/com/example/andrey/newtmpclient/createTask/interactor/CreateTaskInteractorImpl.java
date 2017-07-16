package com.example.andrey.newtmpclient.createTask.interactor;

import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.example.andrey.newtmpclient.activities.AccountActivity;
import com.example.andrey.newtmpclient.entities.Address;
import com.example.andrey.newtmpclient.entities.Task;
import com.example.andrey.newtmpclient.entities.TaskEnum;
import com.example.andrey.newtmpclient.entities.User;
import com.example.andrey.newtmpclient.managers.AddressManager;
import com.example.andrey.newtmpclient.managers.TasksManager;
import com.example.andrey.newtmpclient.managers.UsersManager;
import com.example.andrey.newtmpclient.network.Request;
import com.example.andrey.newtmpclient.storage.DateUtil;
import com.example.andrey.newtmpclient.storage.Updater;

import java.util.Date;
import java.util.List;

import static android.content.ContentValues.TAG;

/**
 * Created by andrey on 14.07.2017.
 */

public class CreateTaskInteractorImpl implements CreateTaskInteractor {
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

    @Override
    public String[] getImportanceString() {
        return new String[]{TaskEnum.STANDART, TaskEnum.INFO, TaskEnum.AVARY, TaskEnum.TIME};
    }

    @Override
    public String[] getTypes() {
        return new String[]{TaskEnum.TAKE_INFO, TaskEnum.ARTF, TaskEnum.UUTE, TaskEnum.ITP, TaskEnum.INSPECTION, TaskEnum.APARTMENT};
    }

    @Override
    public String[] getStatuses() {
        return new String[]{TaskEnum.NEW_TASK};
    }

    @Override
    public String[] getUserNames() {
        return new String[]{usersManager.getUsers().get(0).getLogin()};
    }

    @Override
    public String[] getAddressName() {
        List<Address>addresses = addressManager.getAddresses();
        String[] addressNameArray = new String[addresses.size()];
        for (int i = 0; i < addresses.size(); i++) {
            addressNameArray[i] = addresses.get(i).getAddress();
        }
        return addressNameArray;
    }


    @Override
    public Date getDate() {
        return dateUtil.getDate();
    }

    @Override
    public void createTask(Context context) {
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
        Intent intent = new Intent(context, AccountActivity.class);
        new Updater(context, new Request(task, Request.ADD_TASK_TO_SERVER), intent).execute();
    }

    @Override
    public void setTypeSelected(String type) {
        this.type = type;
    }

    @Override
    public void setImportanceSelected(String importance) {
        this.importance = importance;
    }

    @Override
    public void setStatusSelected(String statusSelected) {
        this.status = statusSelected;
    }

    @Override
    public void setUserSelected(String userName) {
        this.userName = userName;
    }


    @Override
    public void setBody(String body) {
        this.body = body;
    }

    @Override
    public void setDate(String date) {
        this.date = date;
    }

    @Override
    public void setAddress(String address) {
        this.address = address;
    }
}
