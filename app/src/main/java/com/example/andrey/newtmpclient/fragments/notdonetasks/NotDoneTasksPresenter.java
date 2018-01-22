package com.example.andrey.newtmpclient.fragments.notdonetasks;

import android.util.Log;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class NotDoneTasksPresenter {
    private static final String TAG = NotDoneTasksPresenter.class.getSimpleName();
    private NotDoneTasksView view;
    private NotDoneTasksInterActor interActor;

    public NotDoneTasksPresenter(NotDoneTasksView view, NotDoneTasksInterActor interActor) {
        this.view = view;
        this.interActor = interActor;
    }

    void updateTasks() {
        interActor.updateTasks()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(response -> {
                    view.setListToAdapter(response.getTaskList());
                },
                throwable -> Log.e(TAG, throwable.getMessage(), throwable));
    }

    void getSearchedList(String search, boolean done){
        interActor.searchedTasks(search, done)
                .subscribe(tasks -> {

                });
    }

}
