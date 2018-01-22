package com.example.andrey.newtmpclient.fragments.donetasks;

import android.util.Log;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class DoneTasksPresenter {
    private static final String TAG = DoneTasksPresenter.class.getSimpleName();
    private DoneTasksView view;
    private DoneTasksInterActor interActor;

    public DoneTasksPresenter(DoneTasksView view, DoneTasksInterActor interActor) {
        this.view = view;
        this.interActor = interActor;
    }

    void updateTasks() {
        interActor.updateTasks()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(list -> {
                    view.setListToAdapter(list);
                },
                throwable -> Log.e(TAG, throwable.getMessage(), throwable));
    }

    void getSearchedList(String search, boolean done){
        interActor.searchedTasks(search, done)
                .subscribe(tasks -> {
                    view.setListToAdapter(tasks);
                }, throwable -> throwable.printStackTrace());
    }

}
