package com.example.andrey.newtmpclient.createTask;

import android.content.Context;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.AppCompatSpinner;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;

import com.example.andrey.newtmpclient.R;
import com.example.andrey.newtmpclient.entities.TaskEnum;
import com.example.andrey.newtmpclient.fragments.datepicker.DatePickerFragment;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

import static android.content.ContentValues.TAG;

/**
 * Created by andrey on 14.07.2017.
 */

public class CreateTaskPresenterImpl {
    private CreateTaskView view;
    private Context context;
    private CreateTaskInteractorImpl createTaskInteractor;
    private static final String DIALOG_DATE = "date_dialog";
    private static final String FILL_THIS_FIELD = "Вы должны заполнить это поле";
    private static final String CHOOSE_DATE = "Выбрать дату";
    private static final String GET_ADDRESSES = "Получите адреса в нужной вкладке";
    private static final String NOT_DISTRIBUTED = "Не назначена";

    public void setContext(Context context) {
        this.context = context;
    }

    public CreateTaskPresenterImpl(CreateTaskView createTaskView, CreateTaskInteractorImpl createTaskInteractor) {
        this.view = createTaskView;
//        this.context = context;
        this.createTaskInteractor = createTaskInteractor;
    }

    void onDestroy() {
        view = null;
    }

//    void startAccountActivity() {
//        context.startActivity(new Intent(context, AccountActivity.class));
//    }

    void chooseDate(FragmentManager manager) {
        DatePickerFragment dialog = DatePickerFragment.newInstance(createTaskInteractor.getDate());
        dialog.show(manager, DIALOG_DATE);
    }

    private boolean checkAddressName(String addressName) {
        if (TextUtils.isEmpty(addressName)) {
            view.setAnndressNamesCompleteTV(FILL_THIS_FIELD);
            return false;
        } else {
            return true;
        }
    }


    void clickOnTypes(AppCompatSpinner typeSpinner) {
        String[] types = createTaskInteractor.getTypes();
        ArrayAdapter<String> adapter = new ArrayAdapter<>(context, R.layout.spinner_item, types);
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

    void clickOnImportance(AppCompatSpinner importanceSpinner) {
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

    void clickOnStatuses(AppCompatSpinner statusesSpinner) {
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

    private void setSelectedStatusPosition(int position) {
        String[] userNames = createTaskInteractor.getUserNames();
        if (getStatuses()[position].equals(TaskEnum.NEW_TASK)) {
            for (int i = 0; i < userNames.length; i++) {
                if (userNames[i].equals(NOT_DISTRIBUTED)) {
                    view.setUserSelection(i);
                }
            }
        }
        createTaskInteractor.setStatusSelected(getStatuses()[position]);
        Log.i(TAG, "setSelectedStatusPosition: " + getStatuses()[position]);
//        return getStatuses()[position];
    }

    void clickOnUserNames(AppCompatSpinner userNamesSpinner) {
        String[] userNames = createTaskInteractor.getUserNames();
        ArrayAdapter<String> adapter = new ArrayAdapter<>(context, R.layout.spinner_item, userNames);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        userNamesSpinner.setAdapter(adapter);
        userNamesSpinner.setSelection(0);

        userNamesSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position == 0) {
                    CreateTaskPresenterImpl.this.view.setStatusSpinnerSelection(0);
                } else {
                    CreateTaskPresenterImpl.this.view.setStatusSpinnerSelection(1);
                }
                createTaskInteractor.setUserSelected(userNames[position]);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                view.setStatusSpinnerSelection(0);
                createTaskInteractor.setUserSelected(userNames[0]);
            }
        });
    }

    public String[] getImportance() {
        return createTaskInteractor.getImportanceString();
    }

    private boolean checkBody(String bodyText) {
        if (TextUtils.isEmpty(bodyText)) {
            view.setBodyText(FILL_THIS_FIELD);
            return false;
        } else {
            return true;
        }
    }


    void checkValidFields() {
        if (checkAddressName(view.getAddress())) {
            if (checkBody(view.getBody())) {
                if (checkDate(view.getDate())) {
                    createTask();
                }
            }
        }
    }

    private boolean checkDate(String dateText) {
        if (dateText.equals(CHOOSE_DATE) || dateText.equals(FILL_THIS_FIELD)) {
            view.setBodyText(FILL_THIS_FIELD);
            return false;
        } else {
            return true;
        }
    }

    private String[] getStatuses() {
        return createTaskInteractor.getStatuses();
    }

    public void createTask() {
        createTaskInteractor.setAddress(view.getAddress());
        createTaskInteractor.setBody(view.getBody());
        createTaskInteractor.setDate(view.getDate());
        createTaskInteractor.createTask()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe(disposable -> view.showDialog())
                .doOnTerminate(() -> view.hideDialog())
                .subscribe(response -> {
                    Log.i(TAG, "createTask: " + response);
                    view.finishCreateActivity();
                }, throwable -> {
                    throwable.printStackTrace();
                    view.showToast("Ошибка при создании задания");
                });
    }

    void setAddressNameAdapter(AutoCompleteTextView addressNameAdapter) {
        String[] addressNames = createTaskInteractor.getAddressName();
        Log.i(TAG, "setAddressNameAdapter: " + addressNames.length);
        addressNameAdapter.setAdapter(new ArrayAdapter<>(context, android.R.layout.simple_dropdown_item_1line, addressNames));
        checkAddresses(addressNames);
    }

    private void checkAddresses(String[] addresses) {
        if (addresses.length == 0) {
            view.setAnndressNamesCompleteTV(GET_ADDRESSES);
        }
    }

}
