package com.example.andrey.newtmpclient.fragments.one_task_fragment.interactor;

import com.example.andrey.newtmpclient.entities.Comment;
import com.example.andrey.newtmpclient.entities.ContactOnAddress;
import com.example.andrey.newtmpclient.entities.Task;

import java.util.List;

/**
 * Created by andrey on 19.07.2017.
 */

public interface OneTaskFragmentInteractor {
    void initFields();

    List<Comment>getCommentsForTask();

    void checkTaskStatusBtnsEnabled();

    void addComment(String comment, String status);

    String getStatusTask();

    boolean checkIfCommentFilled(String comment);

    List<ContactOnAddress>contactsOnAddress();

    void setOnActionsBtns(OnActionsBtns onActionsBtns);

    void userTakesTask(String changedStatusTask);

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
    }

    interface OnActionsBtns{
        void startActivityAfterCreate(Comment comment, String status);

        void startActivityWithTask(Task task);
    }
}
