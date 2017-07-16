package com.example.andrey.newtmpclient.createTask.presenter;

import android.support.v4.app.FragmentManager;
import android.support.v7.widget.AppCompatSpinner;
import android.widget.AutoCompleteTextView;

import com.example.andrey.newtmpclient.entities.Task;

/**
 * Created by andrey on 14.07.2017.
 */

public interface CreateTaskPresenter {
    void onDestroy();

    void startAccountActivity();

    void chooseDate(FragmentManager manager);

    boolean checkAddressName(String addressName);

    boolean checkBody(String bodyText);

    boolean checkDate(String dateText);

    String[] getStatuses();

    String[] getImportance();

    void createTask();

    void clickOnTypes(AppCompatSpinner typeSpinner);

    void clickOnImportance(AppCompatSpinner importanceSpinner);

    void clickOnStatuses(AppCompatSpinner statusesSpinner);

    void clickOnUserNames(AppCompatSpinner userNamesSpinner);

    void setAddressNameAdapter(AutoCompleteTextView addressNameAdapter);

    void checkValidFields();
}
