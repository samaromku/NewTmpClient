package com.example.andrey.newtmpclient.fragments.one_task_fragment;

import com.example.andrey.newtmpclient.entities.Comment;
import com.example.andrey.newtmpclient.entities.ContactOnAddress;
import com.example.andrey.newtmpclient.rx.TransformerDialog;
import com.example.andrey.newtmpclient.utils.Const;
import com.example.andrey.newtmpclient.utils.Utils;

import java.util.List;

/**
 * Created by andrey on 19.07.2017.
 */

public class OneTaskFragmentPresenter implements OnInitTask {
    private OneTaskView view;
    private OneTaskFragmentInterActor interActor;

    public OneTaskFragmentPresenter(OneTaskView oneTaskView, OneTaskFragmentInterActor interActor) {
        this.view = oneTaskView;
        this.interActor = interActor;
        this.interActor.setOnInitTask(this);
    }

    void setTaskId(int taskId) {
        interActor.setTaskId(taskId);
    }

    void initFields() {
        interActor.initFields();
    }

    @Override
    public void setType(String type) {
        view.setType(type);
    }

    @Override
    public void setImportance(String importance) {
        view.setImportance(importance);
    }

    @Override
    public void setOrgName(String orgName) {
        view.setOrgName(orgName);
    }

    @Override
    public void setAddress(String address) {
        view.setAddress(address);
    }

    @Override
    public void setBody(String taskBody) {
        view.setBody(taskBody);
    }

    @Override
    public void setDeadLine(String deadLine) {
        view.setDeadLine(deadLine);
    }

    @Override
    public void setUserName(String userName) {
        view.setUserName(userName);
    }

    public void onDestroy() {
        view = null;
    }

    List<Comment> getCommentsFromInteractor() {
        return interActor.getCommentsForTask();
    }

    void userTakesTask(String status) {
        interActor.userTakesTask(status)
            .compose(new TransformerDialog<>(view))
            .subscribe(response -> {
                interActor.updateTask()
                        .subscribe(() -> view.startMainStatusChanged());
            }, throwable -> Utils.showError(view, throwable));
    }

    @Override
    public void onSetDisableDistributed(boolean isEnable) {
        view.setDisableDistributed(isEnable);
    }

    @Override
    public void onSetDisableNeedHelp(boolean isEnable) {
        view.setDisableNeedHelp(isEnable);
    }

    @Override
    public void onSetDisableDoing(boolean isEnable) {
        view.setDisableDoing(isEnable);
    }

    @Override
    public void onSetDisableDisagree(boolean isEnable) {
        view.setDisableDisagree(isEnable);
    }

    @Override
    public void onSetDisableDone(boolean isEnable) {
        view.setDisableDone(isEnable);
    }

    List<ContactOnAddress> getContactsFromInteractor() {
        return interActor.contactsOnAddress();
    }

    void addNecessaryCommentTaskStatus(String comment, String taskStatus) {
        if (interActor.checkIfCommentFilled(comment)) {
            interActor.addComment(comment, taskStatus)
                    .compose(new TransformerDialog<>(view))
                    .subscribe(r -> {
                        interActor.addComment()
                                .subscribe(() -> view.startMainStatusChanged());
                    }, throwable -> Utils.showError(view, throwable));
        } else {
            view.setHitComment(Const.FILL_FIELDS);
        }
    }
}
