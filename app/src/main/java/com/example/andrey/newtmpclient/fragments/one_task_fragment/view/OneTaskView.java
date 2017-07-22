package com.example.andrey.newtmpclient.fragments.one_task_fragment.view;

import com.example.andrey.newtmpclient.entities.Comment;
import com.example.andrey.newtmpclient.entities.Task;

/**
 * Created by andrey on 19.07.2017.
 */

public interface OneTaskView {

    void setType(String type);

    void setImportance(String importance);

    void setOrgName(String orgName);

    void setAddress(String address);

    void setBody(String taskBody);

    void setDeadLine(String deadLine);

    void setUserName(String userName);

    void setDisableDistributed(boolean isEnable);

    void setDisableNeedHelp(boolean isEnable);

    void setDisableDoing(boolean isEnable);

    void setDisableDisagree(boolean isEnable);

    void setDisableDone(boolean isEnable);

    void startActivityWithComment(Comment newComment, String status);

    void setHitComment(String hint);

    void startActivityWithTask(Task task);
}
