package com.example.andrey.newtmpclient.activities.createTask;

import android.support.v4.app.FragmentManager;
import android.text.TextUtils;
import android.util.Log;

import com.example.andrey.newtmpclient.base.BasePresenter;
import com.example.andrey.newtmpclient.entities.TaskEnum;
import com.example.andrey.newtmpclient.fragments.datepicker.DatePickerFragment;
import com.example.andrey.newtmpclient.rx.TransformerDialog;
import com.example.andrey.newtmpclient.utils.Utils;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;

import static android.content.ContentValues.TAG;
import static com.example.andrey.newtmpclient.entities.TaskEnum.TIME;
import static com.example.andrey.newtmpclient.storage.Const.WRONG_ADDRESS;

/**
 * Created by andrey on 14.07.2017.
 */

public class CreateTaskPresenter extends BasePresenter {
    private CreateTaskView view;
    private CreateTaskInterActor interactor;
    private static final String DIALOG_DATE = "date_dialog";
    private static final String FILL_THIS_FIELD = "Вы должны заполнить это поле";
    private static final String CHOOSE_DATE = "Выбрать дату";
    private static final String GET_ADDRESSES = "Получите адреса в нужной вкладке";
    private static final String NOT_DISTRIBUTED = "Не назначена";
    private CompositeDisposable cp;

    public CreateTaskPresenter(CreateTaskView createTaskView, CreateTaskInterActor createTaskInteractor) {
        this.view = createTaskView;
        this.interactor = createTaskInteractor;
        cp = new CompositeDisposable();
    }

    void chooseDate(FragmentManager manager) {
        DatePickerFragment dialog = DatePickerFragment.newInstance(interactor.getDate());
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


    void clickOnTypes() {
        cp.add(interactor.getTypes()
                .subscribe(strings -> view.setTypes(strings)));
    }

    void setType(String type) {
        interactor.setTypeSelected(type);
    }

    void setImportance(String importance) {
        interactor.setImportanceSelected(importance);
        if (importance.equals(TIME)) {
            view.setPeriodVisibility();
        } else {
            view.setChooseTimeVisibility();
        }
    }

    void clickOnImportance() {
        cp.add(interactor.getImportanceString()
                .subscribe(strings -> view.setImportance(strings)));
    }

    void setStatus(String status) {
        interactor.setStatusSelected(status);
    }

    void clickOnStatuses() {
        cp.add(interactor.getStatuses()
                .subscribe(strings -> view.setStatuses(strings)));
    }

    void setUserName(String userName) {
        interactor.setUserSelected(userName);
    }

    void setSelectedStatusPosition(int position, String[] statuses) {
        cp.add(interactor.getUserNames()
                .subscribe(userNames -> {
                    if (statuses[position].equals(TaskEnum.NEW_TASK)) {
                        for (int i = 0; i < userNames.length; i++) {
                            if (userNames[i].equals(NOT_DISTRIBUTED)) {
                                view.setUserSelection(i);
                            }
                        }
                    }
                    interactor.setStatusSelected(statuses[position]);
                    Log.i(TAG, "setSelectedStatusPosition: " + statuses[position]);
                }));
    }

    void clickOnUserNames() {
        cp.add(interactor.getUserNames()
                .subscribe(strings -> view.setUserNames(strings)));
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

    public void createTask() {
        interactor.setAddress(view.getAddress());
        interactor.setBody(view.getBody());
        interactor.setDate(view.getDate());
        cp.add(interactor.createTask()
                .compose(new TransformerDialog<>(view))
                .subscribe(response -> view.finishCreateActivity(),
                        throwable -> {
                            Utils.showError(view, throwable);
                            if (throwable.getMessage().equals(WRONG_ADDRESS)) {
                                view.showToast(WRONG_ADDRESS);
                            } else {
                                view.showToast("Ошибка при создании задания");
                            }
                        }));
    }

    void setAddressNameAdapter() {
        cp.add(interactor.getAddressName()
                .subscribe(addressNames -> {
                    Log.i(TAG, "setAddressNameAdapter: " + addressNames.length);
                    view.setAddresses(addressNames);
                    if (addressNames.length == 0) {
                        view.setAnndressNamesCompleteTV(GET_ADDRESSES);
                    }
                }));
    }

    @Override
    public void onDetachView() {
        cp.clear();
    }
}
