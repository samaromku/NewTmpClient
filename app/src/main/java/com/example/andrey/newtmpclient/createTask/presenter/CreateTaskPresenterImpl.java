package com.example.andrey.newtmpclient.createTask.presenter;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.AppCompatSpinner;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;

import com.example.andrey.newtmpclient.R;
import com.example.andrey.newtmpclient.activities.AccountActivity;
import com.example.andrey.newtmpclient.createTask.interactor.CreateTaskInteractor;
import com.example.andrey.newtmpclient.createTask.interactor.CreateTaskInteractorImpl;
import com.example.andrey.newtmpclient.createTask.view.CreateTaskView;
import com.example.andrey.newtmpclient.entities.TaskEnum;
import com.example.andrey.newtmpclient.fragments.datepicker.DatePickerFragment;

import static android.content.ContentValues.TAG;

/**
 * Created by andrey on 14.07.2017.
 */

public class CreateTaskPresenterImpl implements CreateTaskPresenter {
    private CreateTaskView createTaskView;
    private Context context;
    private CreateTaskInteractor createTaskInteractor;
    private static final String DIALOG_DATE = "date_dialog";
    public static final String FILL_THIS_FIELD = "Вы должны заполнить это поле";
    public static final String CHOOSE_DATE = "Выбрать дату";
    public static final String GET_ADDRESSES = "Получите адреса в нужной вкладке";
    public static final String NOT_DISTRIBUTED = "Не назначена";


    public CreateTaskPresenterImpl(CreateTaskView createTaskView, Context context) {
        this.createTaskView = createTaskView;
        this.context = context;
        createTaskInteractor = new CreateTaskInteractorImpl();
    }

    @Override
    public void onDestroy() {
        createTaskView = null;
    }

    @Override
    public void startAccountActivity() {
        context.startActivity(new Intent(context, AccountActivity.class));
    }

    @Override
    public void chooseDate(FragmentManager manager) {
        DatePickerFragment dialog = DatePickerFragment.newInstance(createTaskInteractor.getDate());
        dialog.show(manager, DIALOG_DATE);
    }

    @Override
    public boolean checkAddressName(String addressName) {
        if (TextUtils.isEmpty(addressName)) {
            createTaskView.setAnndressNamesCompleteTV(FILL_THIS_FIELD);
            return false;
        } else {
            return true;
        }
    }


    @Override
    public void clickOnTypes(AppCompatSpinner typeSpinner) {
        String[] types = createTaskInteractor.getTypes();
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(context, R.layout.spinner_item, types);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        typeSpinner.setAdapter(adapter);
        typeSpinner.setSelection(0);

        typeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                createTaskInteractor.setTypeSelected(types[position]);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                createTaskInteractor.setTypeSelected(types[0]);
            }
        });
    }

    @Override
    public void clickOnImportance(AppCompatSpinner importanceSpinner) {
        String[] importances = createTaskInteractor.getImportanceString();
        ArrayAdapter<String> adapter = new ArrayAdapter<>(context, R.layout.spinner_item, importances);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        importanceSpinner.setAdapter(adapter);
        importanceSpinner.setSelection(0);

        importanceSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                createTaskInteractor.setImportanceSelected(importances[position]);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                createTaskInteractor.setImportanceSelected(importances[0]);
            }
        });
    }

    @Override
    public void clickOnStatuses(AppCompatSpinner statusesSpinner) {
        String[] statuses = createTaskInteractor.getStatuses();
        ArrayAdapter<String> adapter = new ArrayAdapter<>(context, R.layout.spinner_item, statuses);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        statusesSpinner.setAdapter(adapter);
        statusesSpinner.setSelection(0);

        statusesSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                setSelectedStatusPosition(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                createTaskInteractor.setStatusSelected(statuses[0]);
            }
        });
    }

    private String setSelectedStatusPosition(int position) {
        String[] userNames = createTaskInteractor.getUserNames();
        if (getStatuses()[position].equals(TaskEnum.NEW_TASK)) {
            for (int i = 0; i < userNames.length; i++) {
                if (userNames[i].equals(NOT_DISTRIBUTED)) {
                    createTaskView.setUserSelection(i);
                }
            }
        }
        createTaskInteractor.setStatusSelected(getStatuses()[position]);
        Log.i(TAG, "setSelectedStatusPosition: " + getStatuses()[position]);
        return getStatuses()[position];
    }

    @Override
    public void clickOnUserNames(AppCompatSpinner userNamesSpinner) {
        String[] userNames = createTaskInteractor.getUserNames();
        ArrayAdapter<String> adapter = new ArrayAdapter<>(context, R.layout.spinner_item, userNames);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        userNamesSpinner.setAdapter(adapter);
        userNamesSpinner.setSelection(0);

        userNamesSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position == 0) {
                    createTaskView.setStatusSpinnerSelection(0);
                } else {
                    createTaskView.setStatusSpinnerSelection(1);
                }
                createTaskInteractor.setUserSelected(userNames[position]);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                createTaskView.setStatusSpinnerSelection(0);
                createTaskInteractor.setUserSelected(userNames[0]);
            }
        });
    }

    @Override
    public String[] getImportance() {
        return createTaskInteractor.getImportanceString();
    }

    @Override
    public boolean checkBody(String bodyText) {
        if (TextUtils.isEmpty(bodyText)) {
            createTaskView.setBodyText(FILL_THIS_FIELD);
            return false;
        } else {
            return true;
        }
    }

    @Override
    public void checkValidFields() {
        if (checkAddressName(createTaskView.getAddress())) {
            if (checkBody(createTaskView.getBody())) {
                if (checkDate(createTaskView.getDate())) {
                    createTask();
                }
            }
        }
    }

    @Override
    public boolean checkDate(String dateText) {
        if (dateText.equals(CHOOSE_DATE) || dateText.equals(FILL_THIS_FIELD)) {
            createTaskView.setBodyText(FILL_THIS_FIELD);
            return false;
        } else {
            return true;
        }
    }

    @Override
    public String[] getStatuses() {
        return createTaskInteractor.getStatuses();
    }

    @Override
    public void createTask() {
        createTaskInteractor.setAddress(createTaskView.getAddress());
        createTaskInteractor.setBody(createTaskView.getBody());
        createTaskInteractor.setDate(createTaskView.getDate());
        createTaskInteractor.createTask(context);
    }

    @Override
    public void setAddressNameAdapter(AutoCompleteTextView addressNameAdapter) {
        String[] addressNames = createTaskInteractor.getAddressName();
        Log.i(TAG, "setAddressNameAdapter: " + addressNames.length);
        addressNameAdapter.setAdapter(new ArrayAdapter<>(context, android.R.layout.simple_dropdown_item_1line, addressNames));
        checkAddresses(addressNames);
    }

    private void checkAddresses(String[] addresses) {
        if (addresses.length == 0) {
            createTaskView.setAnndressNamesCompleteTV(GET_ADDRESSES);
        }
    }

}
