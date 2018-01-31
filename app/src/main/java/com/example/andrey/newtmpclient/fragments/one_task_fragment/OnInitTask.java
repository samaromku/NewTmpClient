package com.example.andrey.newtmpclient.fragments.one_task_fragment;

import com.example.andrey.newtmpclient.entities.Comment;
import com.example.andrey.newtmpclient.entities.Task;

/**
 * Created by savchenko on 31.01.18.
 */

interface OnInitTask {
    void setType(String type);

    void setImportance(String importance);

    void setOrgName(String orgName);

    void setAddress(String address);

    void setBody(String taskBody);

    void setDeadLine(String deadLine);

    void setUserName(String userName);

    void onSetDisableDistributed(boolean isEnable);

    void onSetDisableNeedHelp(boolean isEnable);

    void onSetDisableDoing(boolean isEnable);

    void onSetDisableDisagree(boolean isEnable);

    void onSetDisableDone(boolean isEnable);

//    void startActivityAfterCreate(Comment comment, String status);

//    void startActivityWithTask(Task task);
}
