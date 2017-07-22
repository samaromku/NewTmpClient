package com.example.andrey.newtmpclient.fragments.one_task_fragment.presenter;

import com.example.andrey.newtmpclient.entities.Comment;
import com.example.andrey.newtmpclient.entities.ContactOnAddress;
import com.example.andrey.newtmpclient.entities.Task;
import com.example.andrey.newtmpclient.fragments.one_task_fragment.interactor.OneTaskFragmentInteractor;
import com.example.andrey.newtmpclient.fragments.one_task_fragment.interactor.OneTaskFragmentInteractor.OnInitTask;
import com.example.andrey.newtmpclient.fragments.one_task_fragment.interactor.OneTaskFragmentInteractorImpl;
import com.example.andrey.newtmpclient.fragments.one_task_fragment.view.OneTaskView;
import com.example.andrey.newtmpclient.utils.Const;

import java.util.List;

/**
 * Created by andrey on 19.07.2017.
 */

public class OneTaskFragmentPresenterImpl implements OneTaskFragmentPresenter, OnInitTask, OneTaskFragmentInteractor.OnActionsBtns {
    private OneTaskView oneTaskView;
    private OneTaskFragmentInteractor oneTaskFragmentInteractor;

    public OneTaskFragmentPresenterImpl(OneTaskView oneTaskView, int taskId) {
        this.oneTaskView = oneTaskView;
        this.oneTaskFragmentInteractor = new OneTaskFragmentInteractorImpl(taskId, this);
        oneTaskFragmentInteractor.setOnActionsBtns(this);
    }

    @Override
    public void initFields() {
        oneTaskFragmentInteractor.initFields();
    }

    @Override
    public void setType(String type) {
        oneTaskView.setType(type);
    }

    @Override
    public void setImportance(String importance) {
        oneTaskView.setImportance(importance);
    }

    @Override
    public void setOrgName(String orgName) {
        oneTaskView.setOrgName(orgName);
    }

    @Override
    public void setAddress(String address) {
        oneTaskView.setAddress(address);
    }

    @Override
    public void setBody(String taskBody) {
        oneTaskView.setBody(taskBody);
    }

    @Override
    public void setDeadLine(String deadLine) {
        oneTaskView.setDeadLine(deadLine);
    }

    @Override
    public void setUserName(String userName) {
        oneTaskView.setUserName(userName);
    }

    @Override
    public void onDestroy() {
        oneTaskView = null;
    }

    @Override
    public List<Comment> getCommentsFromInteractor() {
        return oneTaskFragmentInteractor.getCommentsForTask();
    }

    @Override
    public void userTakesTask(String status) {
        oneTaskFragmentInteractor.userTakesTask(status);
    }

    @Override
    public void startActivityWithTask(Task task) {
        oneTaskView.startActivityWithTask(task);
    }

    @Override
    public void onSetDisableDistributed(boolean isEnable) {
        oneTaskView.setDisableDistributed(isEnable);
    }

    @Override
    public void onSetDisableNeedHelp(boolean isEnable) {
        oneTaskView.setDisableNeedHelp(isEnable);
    }

    @Override
    public void onSetDisableDoing(boolean isEnable) {
        oneTaskView.setDisableDoing(isEnable);
    }

    @Override
    public void onSetDisableDisagree(boolean isEnable) {
        oneTaskView.setDisableDisagree(isEnable);
    }

    @Override
    public void onSetDisableDone(boolean isEnable) {
        oneTaskView.setDisableDone(isEnable);
    }

    @Override
    public List<ContactOnAddress> getContactsFromInteractor() {
        return oneTaskFragmentInteractor.contactsOnAddress();
    }

    @Override
    public void addNesessaryCommentTaskStatus(String comment, String taskStatus) {
        if (oneTaskFragmentInteractor.checkIfCommentFilled(comment)) {
            oneTaskFragmentInteractor.addComment(comment, taskStatus);
        } else {
            oneTaskView.setHitComment(Const.FILL_FIELDS);
        }
    }

    @Override
    public String getStatusTaskFromInteractor() {
        return oneTaskFragmentInteractor.getStatusTask();
    }

    @Override
    public void startActivityAfterCreate(Comment comment, String status) {
        oneTaskView.startActivityWithComment(comment, status);
    }

    @Override
    public void changeStatusWithoutComment(String taskStatus) {
        oneTaskFragmentInteractor.addComment("", taskStatus);
    }
}
