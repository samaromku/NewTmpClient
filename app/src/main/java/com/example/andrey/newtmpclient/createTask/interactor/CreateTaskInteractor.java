package com.example.andrey.newtmpclient.createTask.interactor;

import android.content.Context;

import java.util.Date;

/**
 * Created by andrey on 14.07.2017.
 */

public interface CreateTaskInteractor {
    String[] getImportanceString();

    String[] getTypes();

    String[] getStatuses();

    String[] getUserNames();

    String[] getAddressName();

    Date getDate();

    void createTask(Context context);

    void setTypeSelected(String type);

    void setImportanceSelected(String importance);

    void setStatusSelected(String statusSelected);

    void setUserSelected(String userName);

    void setBody(String body);

    void setDate(String date);

    void setAddress(String address);
}
