package com.example.andrey.newtmpclient.activities.createTask;

/**
 * Created by andrey on 14.07.2017.
 */

public interface CreateTaskView {
    void setAnndressNamesCompleteTV(String text);

    void setBodyText(String text);

    String getBody();

    String getDate();

    String getAddress();

//    String getImportance();

//    String getType();

//    String getStatus();

//    String getUser();

    void setStatusSpinnerSelection(int position);

    void setUserSelection(int position);

    void finishCreateActivity();

    void showDialog();

    void hideDialog();

    void showToast(String text);

    void setTypes(String[] types);

    void setImportance(String[] importance);

    void setStatuses(String[]statuses);

    void setUserNames(String[]userNames);

    void setAddresses(String[]addresses);
}
